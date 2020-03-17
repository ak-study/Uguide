package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;

import java.io.IOException;
import java.util.HashMap;

public class OrderFinish implements Order {
    private OrderManger orderManger;
    private String curStatus;

    OrderFinish(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public Object orderOver(String cause, OrderDTO orderDTO){
        orderDTO.setOpinion("订单取消");
        orderManger.setCurState(cause);
        this.curStatus=cause;
        HashMap<Object, Object> map = new HashMap<>();
        try {
            WebSocketServer.sendInfo("订单取消成功",
                    String.valueOf(orderDTO.getUserModel().getUser_id()));
            WebSocketServer.sendInfo("用户已取消订单",
                    String.valueOf(orderDTO.getGuideModel().getGuide_id()));
            map.put("data", orderDTO);
            map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
            orderManger.removeFinishOrder(orderDTO.getUserModel());
        } catch (IOException e) {
            map.put("data","取消失败");
            return map;
        }
        return map;
    }

    @Override
    public String curState() throws Exception {
        return curStatus;
    }
}
