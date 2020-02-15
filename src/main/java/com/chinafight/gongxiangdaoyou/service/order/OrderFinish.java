package com.chinafight.gongxiangdaoyou.service.order;

public class OrderFinish implements Order {
    private OrderManger orderManger;

    public OrderFinish(OrderManger orderManger) {
        this.orderManger = orderManger;
    }

    @Override
    public void orderOver() throws Exception {
        orderManger.setCurState(this.curState());
    }

    @Override
    public String curState() throws Exception {
        return "订单完结";
    }
}
