package com.chinafight.gongxiangdaoyou.service.order;

import com.alibaba.fastjson.JSONArray;
import com.chinafight.gongxiangdaoyou.dto.OrderByGuide;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO2;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideorderMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.dto.OrderDTO;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.model.Guideorder;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class OrderService {
    private static HashMap<String, List<OrderByGuide>> orderMapByGuide = new HashMap<>();
    private GuideMapper guideMapper;
    private UserMapper userMapper;
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
        if (tempGuide==null)
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        orderByGuide.setGuideModel(tempGuide);
        orderByGuide.setCurPosition(city);
//        orderByGuide.setOrderPosition(orderPosition);//设置导游接单点
        OrderService.beginOrderReceive(city, orderByGuide);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object startOrderReceiveTest(String testCity,GuideModel guideModel){
        OrderByGuide orderByGuide = new OrderByGuide();
        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        if (tempGuide==null)
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        orderByGuide.setGuideModel(tempGuide);
        orderByGuide.setCurPosition(testCity);
        OrderService.beginOrderReceive(testCity, orderByGuide);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object stopOrderReceive(GuideModel guideModel,HttpServletRequest request){
        String city = (String) request.getSession().getAttribute("city");
        if (removeGuide(city,guideModel.getGuide_id()).equals("success"))
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
        else
            return CustomerEnum.ERROR_STATUS.getMsgMap();
    }

    /**
     * 游客发布订单,获取愿意接单的导游列表
     *
     * @return 愿意接单的导游的列表
     */
    public Object workingGuideList(String orderDst) {
        HashMap<Object, Object> map = new HashMap<>();
        List<OrderByGuide> orderByGuideList = OrderService.getOrderByGuideList(orderDst);
        if (orderByGuideList == null || orderByGuideList.size() == 0) {
            map.put("data", "该地区无人接单");
            map.put("status", CustomerEnum.ERROR_STATUS.getMsgMap());
            return map;
        }
        map.put("data", orderByGuideList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    public Object selectGuide(GuideModel guideModel, OrderDTO orderDTO) throws IOException {
        String orderMsg = JSONArray.toJSON(orderDTO).toString();
        WebSocketServer.sendInfo("您已经被用户接单",guideModel.getGuide_id().toString());
        WebSocketServer.sendInfo(orderMsg,guideModel.getGuide_id().toString());
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
    private static String removeGuide(String city, Integer guideId) {
        List<OrderByGuide> orderByGuides = orderMapByGuide.get(city);
        if (orderByGuides==null || orderByGuides.size()==0){
            throw new RuntimeException("删除导游接单状态失败");
        }
        for (int i = 0; i < orderByGuides.size(); i++) {
            if (orderByGuides.get(i).getGuideModel().getGuide_id().equals(guideId)) {
                orderByGuides.remove(i);
                return "success";
            }
        }
        throw new RuntimeException("删除导游接单状态失败");
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
        order.setDetailedlocation(orderDTO.getDetailedLocation());
        order.setOrderTime(String.valueOf(System.currentTimeMillis()));
        guideorderMapper.insert(order);
    }

    //获取所有订单
    public Object getAllOrders(){
        List<Guideorder> orders = guideorderMapper.getOrderList();
        return CustomerEnum.NORMAL_STATUS.getMsgMap(orders);
    }

    //获取用户或者导游的正在进行中的订单
    public Object getOngoingOrder(UserModel userModel, GuideModel guideModel){
        OrderManger orderManger = null;
        if (userModel.getUser_id()>0)
        orderManger=OrderManger.getOrderMangerMap().get(userModel.getUser_id());
        else if (guideModel.getGuide_id()>0)
        orderManger=OrderManger.getOrderMangerMapByGuide().get(guideModel.getGuide_id());

        if (orderManger!=null)
            return CustomerEnum.NORMAL_STATUS.getMsgMap(orderManger.getOrderDTO());
        else
            return CustomerEnum.ERROR_STATUS.getMsgMap("该用户无正在进行的订单");
    }

    //获取用户或者导游的完结订单
    public Object getAlreadyOrder(UserModel userModel,GuideModel guideModel){
        List<Guideorder> orders = null;
        if (userModel.getUser_id()>0) {
            orders = guideorderMapper.getOrderByUser(userModel.getUser_id());
        } else if (guideModel.getGuide_id()>0) {
            orders= guideorderMapper.getOrderByGuide(guideModel.getGuide_id());
        }

        //将数据库的订单信息和用户，导游个人信息整合成orderDTO
        ArrayList<Object> orderDTOList = new ArrayList<>();
        if (orders!=null){
            for (Guideorder order : orders) {
                OrderDTO2 orderDTO = new OrderDTO2();
                Integer userId = order.getOrderuser();
                Integer guideId = order.getOrderguide();
                UserModel user = userMapper.findUserById(userId);
                GuideModel guide = guideMapper.selectGuideById(guideId);
                orderDTO.setGuideModel(guide);
                orderDTO.setUserModel(user);
                orderDTO.setGuideorder(order);
                orderDTOList.add(orderDTO);
            }
        }

        if (orders!=null)
            return CustomerEnum.NORMAL_STATUS.getParaMsgMap(orderDTOList);
        else
            return CustomerEnum.ERROR_STATUS.getMsgMap("该用户没有订单");
    }


    public Object getUndoneOrder(){
        ArrayList<Object> undoneOrder = new ArrayList<>();
        Set<Map.Entry<Integer, OrderManger>> entries = OrderManger.getOrderMangerMap().entrySet();
        if (entries.size()<=0){
            return CustomerEnum.ERROR_STATUS.getMsgMap("当前没有任何订单");
        }
        for (Map.Entry<Integer, OrderManger> entry : entries) {
            undoneOrder.add(entry.getValue().getOrderDTO());
        }
        return CustomerEnum.NORMAL_STATUS.getParaMsgMap(undoneOrder);
    }

    public Object getOderById(Integer orderId){
        Guideorder order = guideorderMapper.selectByPrimaryKey(orderId);
        if (order==null) return CustomerEnum.ERROR_STATUS.getParaMsgMap();
        GuideModel guide = guideMapper.selectGuideById(order.getOrderguide());
        UserModel user = userMapper.findUserById(order.getOrderuser());
        return CustomerEnum.NORMAL_STATUS.getParaMsgMap(order,guide,user);
    }

    @Autowired
    public void setGuideMapper(GuideMapper guideMapper) {
        this.guideMapper = guideMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
