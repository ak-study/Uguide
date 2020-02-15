package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.dto.OrderByGuide;
import com.chinafight.gongxiangdaoyou.dto.OrderByUser;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {
    private static HashMap<String, List<OrderByGuide>> orderMapByGuide=new HashMap<>();
    @Autowired
    GuideMapper guideMapper;

    /**
     * 导游点击开始接单按钮
     *
     * @param guideModel    导游信息
     * @param request       获取导游当前位置
     * @return 接单信息
     */
    public Object clickBeginOrderReceive(GuideModel guideModel, HttpServletRequest request) {
        OrderByGuide orderByGuide = new OrderByGuide();
        String city = (String) request.getSession().getAttribute("city");
        //禁止重复点击接单按钮
        if (orderMapByGuide.get(city)!=null)
        for (OrderByGuide byGuide : orderMapByGuide.get(city)) {
            if (byGuide.getGuideModel().getGuide_id().equals(guideModel.getGuide_id()))
                return CustomerEnum.ERROR_USER_EXIST.getMsgMap();
        }

        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        if (tempGuide==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        orderByGuide.setGuideModel(tempGuide);
        orderByGuide.setCurPosition(city);
//        orderByGuide.setOrderPosition(orderPosition);
        OrderService.beginOrderReceive(city, orderByGuide);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    /**
     * 游客发布订单,获取愿意接单的导游列表
     *
     * @return 愿意接单的导游的列表
     */
    public Object workingGuideList(HttpServletRequest request) {
        HashMap<Object, Object> map = new HashMap<>();
        String city = (String) request.getSession().getAttribute("city");
        List<OrderByGuide> orderByGuideList = OrderService.getOrderByGuideList(city);
        if (orderByGuideList.size()==0){
            map.put("data","该地区无人接单");
            map.put("status",CustomerEnum.ERROR_STATUS.getMsgMap());
            return map;
        }
        map.put("data", orderByGuideList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    public Object selectGuide(GuideModel guideModel){
        GuideModel selectedGuide = guideMapper.getGuideById(guideModel);
        //TODO 向被选中的导游发送消息，提示已被选中
        return null;
    }



    //导游开始接单
    private static void beginOrderReceive(String city, OrderByGuide orderByGuide){
        if (orderMapByGuide.get(city)==null){
            ArrayList<OrderByGuide> guideList = new ArrayList<>();
            guideList.add(orderByGuide);
            orderMapByGuide.put(city, guideList);
        }else if (orderMapByGuide.get(city)!=null){
            orderMapByGuide.get(city).add(orderByGuide);
        }
    }

    //获取同一地点下，有意愿接单的导游的列表
    private static List<OrderByGuide> getOrderByGuideList(String city){
        return orderMapByGuide.get(city);
    }
    //导游接单后，在订单列表中删除信息
    public static String removeGuide(String city,Integer guideId){
        List<OrderByGuide> orderByGuides = orderMapByGuide.get(city);
        for (int i=0;i<orderByGuides.size();i++){
            if (orderByGuides.get(i).getGuideModel().getGuide_id().equals(guideId)){
                orderByGuides.remove(i);
                return "success";
            }
        }
        return "error";
    }


}
