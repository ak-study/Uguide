package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;

import java.util.HashMap;

public class PayEnd implements Order {
    private OrderManger orderManger;

    PayEnd(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public Object feedback(String opinion, OrderDTO orderDTO) throws Exception {
        String orderFinishMessage = orderManger.getOrderFinish().curState();
        if (orderFinishMessage==null){
            orderManger.setCurState("订单正常完结");
        }else {
            orderManger.setCurState(orderFinishMessage);
        }
        orderDTO.setOpinion(opinion);
        orderManger.removeFinishOrder(orderDTO.getUserModel());
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    @Override
    public String curState() throws Exception {
        return "已付款";
    }
}
