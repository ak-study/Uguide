package com.chinafight.gongxiangdaoyou.model;

public class GuideModel {
    private Integer guide_id;
    private Integer guide_lv;
    private String guide_name;
    private String guide_phone;
    private Integer guide_like;
    private String guide_password;
    private String guide_avatar;
    private String guide_nick;
    private String guide_card;
    private Integer guide_vip;
    private Integer guide_power;
    private String guide_trueName;

    public String getGuide_trueName() {
        return guide_trueName;
    }

    public void setGuide_id(Integer guide_id) {
        this.guide_id = guide_id;
    }

    public void setGuide_lv(Integer guide_lv) {
        this.guide_lv = guide_lv;
    }

    public void setGuide_trueName(String guide_trueName) {
        this.guide_trueName = guide_trueName;
    }

    public String getGuide_password() {
        return guide_password;
    }

    public void setGuide_password(String guide_password) {
        this.guide_password = guide_password;
    }

    @Override
    public String toString() {
        return "GuideModel{" +
                "guide_id=" + guide_id +
                ", guide_lv=" + guide_lv +
                ", guide_name='" + guide_name + '\'' +
                ", guide_phone=" + guide_phone +
                ", guide_like=" + guide_like +
                ", guide_avatar='" + guide_avatar + '\'' +
                ", guide_nick='" + guide_nick + '\'' +
                ", guide_card=" + guide_card +
                ", guide_vip=" + guide_vip +
                ", guide_power=" + guide_power +
                '}';
    }

    public Integer getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(int guide_id) {
        this.guide_id = guide_id;
    }

    public Integer getGuide_lv() {
        return guide_lv;
    }

    public void setGuide_lv(int guide_lv) {
        this.guide_lv = guide_lv;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
    }

    public String getGuide_phone() {
        return guide_phone;
    }

    public void setGuide_phone(String guide_phone) {
        this.guide_phone = guide_phone;
    }

    public Integer getGuide_like() {
        return guide_like;
    }

    public void setGuide_like(Integer guide_like) {
        this.guide_like = guide_like;
    }

    public String getGuide_avatar() {
        return guide_avatar;
    }

    public void setGuide_avatar(String guide_avatar) {
        this.guide_avatar = guide_avatar;
    }

    public String getGuide_nick() {
        return guide_nick;
    }

    public void setGuide_nick(String guide_nick) {
        this.guide_nick = guide_nick;
    }

    public String getGuide_card() {
        return guide_card;
    }

    public void setGuide_card(String guide_card) {
        this.guide_card = guide_card;
    }

    public Integer getGuide_vip() {
        return guide_vip;
    }

    public void setGuide_vip(Integer guide_vip) {
        this.guide_vip = guide_vip;
    }

    public Integer getGuide_power() {
        return guide_power;
    }

    public void setGuide_power(Integer guide_power) {
        this.guide_power = guide_power;
    }
}
