package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.OrderService;
import com.chinafight.gongxiangdaoyou.service.order.OrderManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RestController
public class OrderController {
    private OrderManger orderManger;
    @Autowired
    OrderService orderService;

    /**
     * 导游开始接单，加入接单导游列表
     * @param guideModel 导游信息
     */
    @GetMapping("starOrderReceive")
    public Object starOrderReceive(GuideModel guideModel,HttpServletRequest request){
        return orderService.clickBeginOrderReceive(guideModel,request);
    }
    /**
     *订单创建
     * @param userModel 订单的创建人
     * @return 返回正在接单的导游列表
     */
    @GetMapping("createOrder")
    public Object createOrder(UserModel userModel, HttpServletRequest request) throws Exception {
        orderManger=new OrderManger(userModel);
        return orderManger.workingGuideList(request);
    }

    /**
     * 用户选择导游
     * @param guideModel 被选择的导游的信息
     */
    @GetMapping("selectGuide")
    public Object selectGuide(GuideModel guideModel) throws Exception {
        if (orderManger==null){
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        return orderManger.orderReceive(guideModel);
    }

    /**
     * 用户支付
     */
    @GetMapping("userPay")
    public Object userPay() throws Exception {
        if (orderManger==null){
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        return orderManger.pay();
    }

    /**
     *导游结单
     */
    @GetMapping("orderFinish")
    public Object orderFinish() throws Exception{
        if (orderManger==null){
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        return orderManger.orderFinish();
    }
}
