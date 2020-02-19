package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.dto.OrderByGuide;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideorderMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.model.Guideorder;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.order.OrderManger;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {
    private static HashMap<String, List<OrderByGuide>> orderMapByGuide = new HashMap<>();
    private GuideMapper guideMapper;
    @Autowired
    GuideorderMapper guideorderMapper;

    /**
     * 导游点击开始接单按钮
     *
     * @param guideModel 导游信息
     * @param request    获取导游当前位置
     * @return 接单信息
     */
    public Object clickBeginOrderReceive(GuideModel guideModel, HttpServletRequest request) {
        OrderByGuide orderByGuide = new OrderByGuide();
        String city = (String) request.getSession().getAttribute("city");
        //禁止重复点击接单按钮
        if (orderMapByGuide.get(city) != null)
            for (OrderByGuide byGuide : orderMapByGuide.get(city)) {
                if (byGuide.getGuideModel().getGuide_id().equals(guideModel.getGuide_id()))
                    return CustomerEnum.ERROR_USER_EXIST.getMsgMap();
            }

        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        //判断导游是否登录
        if (Utils.guideLoginMap.get(tempGuide.getGuide_name())==null)
            return CustomerEnum.ERROR_USER_LOGIN.getMsgMap();

        orderByGuide.setGuideModel(tempGuide);
        orderByGuide.setCurPosition(city);
//        orderByGuide.setOrderPosition(orderPosition);//设置导游接单点
        OrderService.beginOrderReceive(city, orderByGuide);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    /**
     * 游客发布订单,获取愿意接单的导游列表
     *
     * @return 愿意接单的导游的列表
     */
    public Object workingGuideList(HttpServletRequest request, UserModel userModel) {
        HashMap<Object, Object> map = new HashMap<>();
        String city = (String) request.getSession().getAttribute("city");
        List<OrderByGuide> orderByGuideList = OrderService.getOrderByGuideList(city);
        if (orderByGuideList == null || orderByGuideList.size() == 0) {
            OrderManger.getOrderMangerMap().remove(userModel.getUser_id());//删除订单
            map.put("data", "该地区无人接单");
            map.put("status", CustomerEnum.ERROR_STATUS.getMsgMap());
            return map;
        }
        map.put("data", orderByGuideList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    public Object selectGuide(GuideModel guideModel, OrderDTO orderDTO) throws IOException {
        WebSocketServer.sendInfo("您已经被用户接单",guideModel.getGuide_id().toString());
        HashMap<Object, Object> map = new HashMap<>();
        orderDTO.setOrderGuide(orderDTO.getGuideModel().getGuide_id());
        orderDTO.setOrderUser(orderDTO.getUserModel().getUser_id());
        removeGuide(orderDTO.getOrderFrom(),guideModel.getGuide_id());
        map.put("data", orderDTO);
        map.put("status",CustomerEnum.NORMAL_STATUS.getMsgMap());
        System.out.println(map);
        return map;
    }


    //导游开始接单
    private static void beginOrderReceive(String city, OrderByGuide orderByGuide) {
        if (orderMapByGuide.get(city) == null) {
            ArrayList<OrderByGuide> guideList = new ArrayList<>();
            guideList.add(orderByGuide);
            orderMapByGuide.put(city, guideList);
        } else if (orderMapByGuide.get(city) != null) {
            orderMapByGuide.get(city).add(orderByGuide);
        }
    }

    //获取同一地点下，有意愿接单的导游的列表
    private static List<OrderByGuide> getOrderByGuideList(String city) {
        return orderMapByGuide.get(city);
    }

    //导游接单后，在订单列表中删除信息
    public static String removeGuide(String city, Integer guideId) {
        List<OrderByGuide> orderByGuides = orderMapByGuide.get(city);
        for (int i = 0; i < orderByGuides.size(); i++) {
            if (orderByGuides.get(i).getGuideModel().getGuide_id().equals(guideId)) {
                orderByGuides.remove(i);
                return "success";
            }
        }
        return "error";
    }

    public void insertOrder(OrderDTO orderDTO){
        Guideorder order = new Guideorder();
        order.setOpinion(orderDTO.getOpinion());
        order.setOrderfrom(orderDTO.getOrderFrom());
        order.setOrderdst(orderDTO.getOrderDst());
        order.setOrderprice(orderDTO.getOrderPrice());
        order.setOrderstatus(orderDTO.getOrderStatus());
        order.setOrderguide(orderDTO.getOrderGuide());
        order.setOrderuser(orderDTO.getOrderUser());
        guideorderMapper.insert(order);
    }

    public Object getUndoneOrder(){
        return CustomerEnum.NORMAL_STATUS.getParaMsgMap(OrderManger.getOrderMangerMap());
    }

    @Autowired
    public void setGuideMapper(GuideMapper guideMapper) {
        this.guideMapper = guideMapper;
    }
}
