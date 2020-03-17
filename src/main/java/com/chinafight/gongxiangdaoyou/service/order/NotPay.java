package com.chinafight.gongxiangdaoyou.service.order;

import com.alibaba.fastjson.JSONArray;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;

import java.util.HashMap;

public class NotPay implements Order{
    private OrderManger orderManger;

    NotPay(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public Object pay(OrderDTO orderDTO) throws Exception {
        orderManger.setCurState(orderManger.getPayEnd().curState());//改变状态
        //默认用户付款
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data", orderDTO);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        WebSocketServer.sendInfo("付款成功",
                String.valueOf(orderDTO.getUserModel().getUser_id()));//向用户发送消息
        WebSocketServer.sendInfo("用户已付款",
                String.valueOf(orderDTO.getGuideModel().getGuide_id()));//向导游发送付款消息
//        String orderMsg = JSONArray.toJSON(orderDTO).toString();
//        WebSocketServer.sendInfo(orderMsg,orderDTO.getGuideModel().getGuide_id().toString());//向导游发送用户订单信息
        return map;
    }

    @Override
    public String curState() throws Exception {
        return "待付款";
    }
}
