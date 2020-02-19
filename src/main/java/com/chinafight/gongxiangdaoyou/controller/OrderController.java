package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.eunm.OrderEnum;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.OrderService;
import com.chinafight.gongxiangdaoyou.service.order.OrderManger;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Controller
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    GuideMapper guideMapper;
    @Autowired
    UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 导游开始接单，加入接单导游列表
     *
     * @param guideModel 导游信息
     */
    @GetMapping("starOrderReceive")
    public Object starOrderReceive(GuideModel guideModel, HttpServletRequest request) {
        logger.info("导游开始接单..."+guideModel.getGuide_id());
        return orderService.clickBeginOrderReceive(guideModel, request);
    }

    /**
     * 订单创建
     *
     * @param userModel 订单的创建人(创建人id)
     * @param orderDTO  订单信息（价格，目的地，出发地）
     * @return 返回正在接单的导游列表
     */
    @GetMapping("createOrder")
    public Object createOrder(UserModel userModel, HttpServletRequest request, OrderDTO orderDTO) throws Exception {
        UserModel tempUser = userMapper.getUserById(userModel);
        UserModel key = Utils.userLoginMap.get(tempUser.getUser_name());
        if (key == null) return CustomerEnum.ERROR_USER_LOGIN.getMsgMap();//判断该用户是否登录

        if (OrderManger.getOrderMangerMap().get(tempUser.getUser_id()) == null) {//防止用户重复创建订单
            OrderManger orderManger = new OrderManger(tempUser);
            orderManger.setOrderMessage(orderDTO.getOrderPrice(),
                    Utils.getCurCityName(request), orderDTO.getOrderDst());
            return orderManger.workingGuideList(request,tempUser);//返回正在接单的导游列表
        }
        logger.info("用户创建订单，返回接单导游列表..."+tempUser.getUser_name());
        return OrderEnum.ORDER_EXIT.getMsgMap();
    }

    /**
     * 用户选择导游，并且通知被选择的导游当前信息
     *
     * @param guideModel 被选择的导游的信息(导游id)
     */
    @GetMapping("selectGuide")
    public Object selectGuide(GuideModel guideModel, UserModel userModel) throws Exception {
        OrderManger orderManger = OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        if (orderManger == null) {
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        logger.info("用户选择导游..."+tempGuide.getGuide_name());
        return orderManger.orderReceive(tempGuide, orderManger.getOrderDTO());
    }

    /**
     * @param userModel 用户id
     * @return 订单支付完成，进入提意见阶段
     */
    @GetMapping("userPay")
    public Object userPay(UserModel userModel) throws Exception {
        OrderManger orderManger = OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        if (orderManger == null) {
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        logger.info("用户支付..."+orderManger.getOrderDTO().getUserModel().getUser_name());
        return orderManger.orderPay(orderManger.getOrderDTO());
    }


    /**
     * 订单完成后，提出建议,并将数据输入到数据库
     *
     * @param userModel 用户id
     * @param opinion   意见信息
     */
    @GetMapping("OrderOpinion")
    public Object OrderOpinion(UserModel userModel, String opinion) throws Exception {
        OrderManger orderManger = OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        if (orderManger == null) {
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        Object map = orderManger.feedback(opinion, orderManger.getOrderDTO());
        orderService.insertOrder(orderManger.getOrderDTO());
        logger.info("用户反馈..."+opinion);
        return map;
    }

    /**
     * 订单非正常态完结（用户主动取消订单，订单超时...）
     */
    @GetMapping("orderFinish")
    public Object orderFinish(UserModel userModel, String cause) {
        OrderManger orderManger = OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        if (orderManger == null) {
            return CustomerEnum.ERROR_EXCEPTION_ORDER_NULL.getMsgMap();
        }
        orderManger.getOrderDTO().setOpinion("订单取消");
        Object map = orderManger.orderFinish(cause, orderManger.getOrderDTO());
        orderService.insertOrder(orderManger.getOrderDTO());
        logger.info("订单非正常完结..."+cause);
        return map;
    }

    /**
     * @return 获取用户取消订单的原因
     */
    @GetMapping("getOrderCancelCause")
    public Object getOrderCancelCause() {
        return Arrays.asList(OrderEnum.ORDER_CANCEL_GUIDE.getMsgMap(),
                OrderEnum.ORDER_CANCEL_GUIDE_2.getMsgMap(),
                OrderEnum.ORDER_CANCEL_USER.getMsgMap(),
                OrderEnum.ORDER_TIME_OVER.getMsgMap());
    }

    @GetMapping("getUndoneOrder")
    public Object getUndoneOrder() {
        return orderService.getUndoneOrder();
    }

    private static OrderManger getOrderManager(UserModel userModel) {
        return OrderManger.getOrderMangerMap().get(userModel.getUser_id());
    }
}
