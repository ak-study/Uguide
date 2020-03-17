package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.eunm.OrderEnum;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.order.OrderService;
import com.chinafight.gongxiangdaoyou.service.order.OrderManger;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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


    /**
     * 导游开始接单，加入接单导游列表
     * @param guideModel 导游信息
     */
    @GetMapping("starOrderReceive")
    public Object starOrderReceive(GuideModel guideModel, HttpServletRequest request) {
        Utils.getLogger().info("导游开始接单..."+guideModel.getGuide_id());
        return orderService.clickBeginOrderReceive(guideModel, request);
    }

    @GetMapping("testGuideReceive")
    public Object testGuideReceive(GuideModel guideModel,String testCity){
        return orderService.startOrderReceiveTest(testCity,guideModel);
    }

    /**
     * 导游停止接单
     * @param guideModel 导游id
     * @param request 获取导游所在地
     */
    @GetMapping("stopOrderReceive")
    public Object stopOrderReceive(GuideModel guideModel,HttpServletRequest request){
        Utils.getLogger().info("导游取消接单..."+guideModel.getGuide_id());
        return orderService.stopOrderReceive(guideModel,request);
    }

    /**
     * 订单创建
     * @return 返回正在接单的导游列表
     */
    @GetMapping("getOnlineGuide")
    public Object getOnlineGuide(String orderDst) {
        Utils.getLogger().info("用户获取在"+ orderDst+"接单的导游列表");
        return orderService.workingGuideList(orderDst);
    }

    /**
     * 用户选择导游后创建订单，并且返回该订单的信息
     * @param guideModel 导游id
     * @param userModel 用户id
     * @param orderDTO 目的地，详细目的地，价格（会根据传入的目的地查找在目的地接单的导游,传入详细目的地如具体景点）
     * @param request 获取用户所在地
     * @return 订单信息
     */
    @GetMapping("selectGuideAndCreateOrder")
    public Object selectGuideAndCreateOrder(GuideModel guideModel, UserModel userModel,
                                            OrderDTO orderDTO,HttpServletRequest request) throws Exception {
        UserModel tempUser = userMapper.getUserById(userModel);
        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        if (tempGuide==null || tempUser==null)
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        if (OrderManger.getOrderMangerMap().get(tempUser.getUser_id()) == null){
            OrderManger orderManger = new OrderManger(tempUser);
            orderManger.setOrderMessage(orderDTO.getOrderPrice(), Utils.getCurCityName(request),
                    orderDTO.getOrderDst(),orderDTO.getDetailedLocation());
            Utils.getLogger().info("用户选择导游..."+tempGuide.getGuide_name());
            Utils.getLogger().info("创建订单成功");
            return orderManger.orderReceive(tempGuide, orderManger.getOrderDTO());
        }
        return OrderEnum.ORDER_EXIT.getMsgMap();
    }

    /**
     * @param userModel 用户id
     * @return 订单支付完成，进入提意见阶段
     */
    @GetMapping("userPay")
    public Object userPay(UserModel userModel) throws Exception {
        OrderManger orderManger = getOrderManager(userModel);
        Utils.getLogger().info(orderManger.getOrderDTO().getUserModel().getUser_name()+"用户完成支付...");
        return orderManger.orderPay(orderManger.getOrderDTO());
    }


    /**
     * 订单完成后，提出建议,并将数据输入到数据库
     * @param userModel 用户id
     * @param opinion   意见信息
     */
    @GetMapping("OrderOpinion")
    public Object OrderOpinion(UserModel userModel, String opinion) throws Exception {
        OrderManger orderManger = getOrderManager(userModel);
        Object map = orderManger.feedback(opinion, orderManger.getOrderDTO());
        Utils.getLogger().info(orderManger.getOrderDTO().getUserModel().getUser_name()+"用户反馈..."+opinion);
        orderService.insertOrder(orderManger.getOrderDTO());
        return map;
    }

    /**
     * 订单非正常态完结（用户主动取消订单，订单超时...）
     */
    @GetMapping("orderFinish")
    public Object orderFinish(UserModel userModel, String cause) {
        OrderManger orderManger = getOrderManager(userModel);
        orderManger.getOrderDTO().setOpinion("订单取消");
        Object map = orderManger.orderFinish(cause, orderManger.getOrderDTO());
        Utils.getLogger().info(orderManger.getOrderDTO().getUserModel().getUser_name()+"的订单非正常完结..." +
                "原因："+cause);
        orderService.insertOrder(orderManger.getOrderDTO());
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
                OrderEnum.ORDER_TIME_OVER.getMsgMap(),
                OrderEnum.ORDER_OTHER.getMsgMap());
    }

    /**
     *
     * @return 获取所有未完成的订单
     */
    @GetMapping("getUndoneOrder")
    public Object getUndoneOrder() {
        return orderService.getUndoneOrder();
    }

    /**
     *
     * @return 获取所有已经完成的订单（数据库内）
     */
    @GetMapping("getAllAlreadyOrders")
    public Object getAllOrders(){
        return orderService.getAllOrders();
    }

    /**
     * 查询用户或者导游正在进行中的订单，如果查询用户则填如用户id，导游填0，如（user_id=2001,guide_id=0）
     * @param userModel 用户id
     * @param guideModel 导游id
     * @return 用户或者导游正在进行中的订单
     */
    @GetMapping("getOngoingOrder")
    public Object getOngoingOrder(UserModel userModel,GuideModel guideModel){
        return orderService.getOngoingOrder(userModel,guideModel);
    }

    /**
     * 查询用户或者导游已经完成的订单，如果查询用户则填如用户id，导游填0，如（user_id=2001,guide_id=0）
     * @param userModel 用户id
     * @param guideModel 导游id
     * @return 用户或者导游已经完成的订单
     */
    @GetMapping("getAlreadyOrder")
    public Object getAlreadyOrder(UserModel userModel,GuideModel guideModel){
        return orderService.getAlreadyOrder(userModel,guideModel);
    }

    @GetMapping("privateChat")
    public Object privateChat(String message,String id){
        if (WebSocketServer.privateChat(message, id)==-1)
            return CustomerEnum.ERROR_STATUS.getMsgMap("私聊失败,该用户可能未上线！");
        else
            return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    @GetMapping("groupChat")
    public Object groupChat(String message) {
        WebSocketServer.groupChat(message);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    @GetMapping("getOrderById")
    public Object getOrderById(Integer orderId){
        return orderService.getOderById(orderId);
    }


    private static OrderManger getOrderManager(UserModel userModel) {
        OrderManger orderManger = OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        if (orderManger==null)
            throw new RuntimeException("订单不存在");
        return orderManger;
    }
}
