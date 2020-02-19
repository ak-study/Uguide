package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 状态: 已发布
 */
public class Released implements Order{
    private boolean isOverTime=false;//是否超时
    private OrderManger orderManger;
    @Autowired
    OrderService orderService=new OrderService();
    Released(OrderManger orderManger){
        this.orderManger = orderManger;
    }
    @Override
    public void startTime() throws Exception {
        new Thread(() -> {
            try {
                Thread.sleep(1000000);
                isOverTime=true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean isOverTime() {
        return isOverTime;
    }

    @Override
    public Object orderReceiving(GuideModel guideModel, OrderDTO orderDTO) throws Exception {
        orderManger.setCurState(orderManger.getNotPay().curState());//用户订单被接收后，改变订单当前状态
        return orderService.selectGuide(guideModel, orderDTO);//通知导游 已被接单
    }

    @Override
    public String curState() throws Exception {
        return "订单已发布";
    }
}
