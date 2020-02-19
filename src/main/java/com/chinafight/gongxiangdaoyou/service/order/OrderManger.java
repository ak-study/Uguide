package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderManger implements Order {
    private static List<String> opinionList=new ArrayList<>();
    private static Map<Integer,OrderManger> orderMangerMap=new HashMap<>();
    private OrderDTO orderDTO = new OrderDTO();
    private String curState;
    private Released released=new Released(this);
    private NotPay notPay=new NotPay(this);
    private PayEnd payEnd=new PayEnd(this);
    private OrderFinish orderFinish=new OrderFinish(this);
    private OrderService orderService=new OrderService();
    public OrderManger(UserModel userModel) throws Exception {
        orderDTO.setUserModel(userModel);
        this.setCurState(curState);
        orderMangerMap.put(userModel.getUser_id(),this);
    }

    @Override
    public void setOrderMessage(String price, String from, String dst) throws Exception {
        this.setCurState(released.curState());
        this.orderDTO.setOrderPrice(price);
        this.orderDTO.setOrderFrom(from);
        this.orderDTO.setOrderDst(dst);
    }

    public Object orderReceive(GuideModel guideModel, OrderDTO orderDTO) throws Exception {
        orderDTO.setGuideModel(guideModel);
        return released.orderReceiving(guideModel, orderDTO);
    }

    public Object orderPay(OrderDTO orderDTO) throws Exception {
        return notPay.pay(orderDTO);
    }

    public Object orderFinish(String cause, OrderDTO orderDTO) {
        return orderFinish.orderOver(cause, orderDTO);
    }

    public Object feedback(String opinion, OrderDTO orderDTO) throws Exception{
        return payEnd.feedback(opinion, orderDTO);
    }

    public Object workingGuideList(HttpServletRequest request,UserModel userModel){
        return orderService.workingGuideList(request,userModel);
    }

    @Override
    public String curState() throws Exception {
        return "订单创建";
    }

    public void removeFinishOrder(UserModel userModel){
        getOrderMangerMap().remove(userModel.getUser_id());
    }

    public static Map<Integer,OrderManger> getOrderMangerMap(){
        return orderMangerMap;
    }

    public static List<String> getOpinionList() {
        return opinionList;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public String getCurState() {
        return curState;
    }

    public void setCurState(String curState) {
        orderDTO.setOrderStatus(curState);
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
