package com.chinafight.gongxiangdaoyou.model;


import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;

public class OrderModel {
    private Integer order_id;
    private Integer order_user;
    private Integer order_guide;
    private Float order_price;
    private String order_from;
    private String order_dst;
    private GuideModel guideModel;
    private UserModel userModel;

    @Override
    public String toString() {
        return "OrderModel{" +
                "order_id=" + order_id +
                ", order_user=" + order_user +
                ", order_guide=" + order_guide +
                ", order_price=" + order_price +
                ", order_from='" + order_from + '\'' +
                ", order_dst='" + order_dst + '\'' +
                '}';
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

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getOrder_user() {
        return order_user;
    }

    public void setOrder_user(Integer order_user) {
        this.order_user = order_user;
    }

    public Integer getOrder_guide() {
        return order_guide;
    }

    public void setOrder_guide(Integer order_guide) {
        this.order_guide = order_guide;
    }

    public Float getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Float order_price) {
        this.order_price = order_price;
    }

    public String getOrder_from() {
        return order_from;
    }

    public void setOrder_from(String order_from) {
        this.order_from = order_from;
    }

    public String getOrder_dst() {
        return order_dst;
    }

    public void setOrder_dst(String order_dst) {
        this.order_dst = order_dst;
    }
}
