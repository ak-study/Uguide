package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;

import java.util.HashMap;

public class NotPay implements Order{
    private OrderManger orderManger;

    public NotPay(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public Object pay() throws Exception {
        orderManger.setCurState(orderManger.getPayEnd().curState());//改变状态
        //默认用户付款
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data","用户已付款");
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    @Override
    public String curState() throws Exception {
        return "待付款";
    }
}
