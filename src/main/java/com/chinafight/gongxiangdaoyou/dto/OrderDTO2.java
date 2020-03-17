package com.chinafight.gongxiangdaoyou.dto;

import com.chinafight.gongxiangdaoyou.model.Guideorder;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;

public class OrderDTO2 {
    private Guideorder guideorder;
    private UserModel userModel;
    private GuideModel guideModel;

    public Guideorder getGuideorder() {
        return guideorder;
    }

    public void setGuideorder(Guideorder guideorder) {
        this.guideorder = guideorder;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public GuideModel getGuideModel() {
        return guideModel;
    }

    public void setGuideModel(GuideModel guideModel) {
        this.guideModel = guideModel;
    }
}
