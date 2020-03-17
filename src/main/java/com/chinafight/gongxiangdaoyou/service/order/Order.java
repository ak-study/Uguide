package com.chinafight.gongxiangdaoyou.service.order;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;

public interface Order {
    /**
     * 用户自定义订单的价格，出发地，目的地
     * @param price 订单价格
     * @param from 订单的出发地
     * @param dst 订单的目的地
     * @throws Exception 如果为实现则抛出异常
     */
    default void setOrderMessage(String price, String from, String dst,String detailedLocation) throws Exception {
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }

    /**
     * 订单超时
     */
    default void startTime() throws Exception {
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }

    /**
     * 订单完结
     */
    default Object orderOver(String cause, OrderDTO orderDTO) throws Exception{
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }

    /**
     * 用户付款
     */
    default Object pay(OrderDTO orderDTO) throws Exception {
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }

    /**
     * 用户反馈
     * @return
     */
    default Object feedback(String opinion, OrderDTO orderDTO) throws Exception{
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }

    /**
     * 导游接单
     * @return
     */
    default Object orderReceiving(GuideModel guideModel, OrderDTO orderDTO) throws Exception {
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }


    /**
     * 当前状态
     */
    default String curState() throws Exception{
        throw new Exception(CustomerEnum.ERROR_STATUS.getMsg());
    }
}
