package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.model.OrderModel;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class OrderManger implements Order {
    private OrderModel orderModel = new OrderModel();
    private String curState;
    private Released released=new Released(this);
    private NotPay notPay=new NotPay(this);
    private PayEnd payEnd=new PayEnd(this);
    private OrderFinish orderFinish=new OrderFinish(this);
    private OrderService orderService=new OrderService();
    public OrderManger(UserModel userModel) throws Exception {
        orderModel.setUserModel(userModel);
        this.setCurState(curState);
    }

    @Override
    public void setOrderMessage(Float price, String from, String dst) throws Exception {
        this.setCurState(released.curState());
        this.orderModel.setOrder_price(price);
        this.orderModel.setOrder_from(from);
        this.orderModel.setOrder_dst(dst);
    }

    public Object orderReceive(GuideModel guideModel) throws Exception {
        released.orderReceiving(guideModel);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object orderPay() throws Exception {
        return notPay.pay();
    }

    public Object orderFinish() throws Exception {
        orderFinish.orderOver();
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object feedback() throws Exception{
        return payEnd.feedback();
    }

    public Object workingGuideList(HttpServletRequest request){
        return orderService.workingGuideList(request);
    }

    @Override
    public String curState() throws Exception {
        return "订单创建";
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        this.curState = curState;
    }

    public Released getReleased() {
        return released;
    }

    public NotPay getNotPay() {
        return notPay;
    }

    public PayEnd getPayEnd() {
        return payEnd;
    }

    public OrderFinish getOrderFinish() {
        return orderFinish;
    }
}
