package com.chinafight.gongxiangdaoyou.dto;

import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;

public class OrderByGuide {
    private GuideModel guideModel;
    private String curPosition;//当前位置
    private String orderPosition;//可接单范围

    public GuideModel getGuideModel() {
        return guideModel;
    }

    public void setGuideModel(GuideModel guideModel) {
        this.guideModel = guideModel;
    }

    public String getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(String curPosition) {
        this.curPosition = curPosition;
    }

    public String getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(String orderPosition) {
        this.orderPosition = orderPosition;
    }
}
