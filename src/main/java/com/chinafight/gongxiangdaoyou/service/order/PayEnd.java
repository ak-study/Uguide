package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;

import java.util.HashMap;

public class PayEnd implements Order {
    private OrderManger orderManger;

    public PayEnd(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public Object feedback() throws Exception {
        orderManger.setCurState(orderManger.getOrderFinish().curState());
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data","请输入你的反馈，功能为实现");
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    @Override
    public String curState() throws Exception {
        return "已付款";
    }
}
