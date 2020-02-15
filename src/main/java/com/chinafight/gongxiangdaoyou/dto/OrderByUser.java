package com.chinafight.gongxiangdaoyou.dto;

import com.chinafight.gongxiangdaoyou.model.profile.UserModel;

public class OrderByUser {
    private String curPosition;//当前位置
    private UserModel userModel;
    private String bestPrice;

    public String getCurPosition() {
        return curPosition;
    }

    public void setCurPosition(String curPosition) {
        this.curPosition = curPosition;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getBestPrice() {
        return bestPrice;
    }

    public void setBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
    }
}
