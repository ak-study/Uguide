package com.chinafight.gongxiangdaoyou.dto;


import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;

public class OrderDTO {
    private Integer orderId;
    private Integer orderUser;
    private Integer orderGuide;
    private String orderPrice;
    private String orderFrom;
    private String orderDst;
    private GuideModel guideModel;
    private UserModel userModel;
    private String orderStatus;
    private String opinion;
    private String detailedLocation;

    @Override
    public String toString() {
        return "OrderModel{" +
                "order_id=" + orderId +
                ", order_user=" + orderUser +
                ", order_guide=" + orderGuide +
                ", order_price=" + orderPrice +
                ", order_from='" + orderFrom + '\'' +
                ", order_dst='" + orderDst + '\'' +
                ", guideModel=" + guideModel +
                ", userModel=" + userModel +
                ", orderStatus='" + orderStatus + '\'' +
                ", opinion='" + opinion + '\'' +
                '}';
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public GuideModel getGuideModel() {
        return guideModel;
    }

    public void setGuideModel(GuideModel guideModel) {
        this.guideModel = guideModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(Integer orderUser) {
        this.orderUser = orderUser;
    }

    public Integer getOrderGuide() {
        return orderGuide;
    }

    public void setOrderGuide(Integer orderGuide) {
        this.orderGuide = orderGuide;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderDst() {
        return orderDst;
    }

    public void setOrderDst(String orderDst) {
        this.orderDst = orderDst;
    }

    public String getDetailedLocation() {
        return detailedLocation;
    }

    public void setDetailedLocation(String detailedLocation) {
        this.detailedLocation = detailedLocation;
    }
}
