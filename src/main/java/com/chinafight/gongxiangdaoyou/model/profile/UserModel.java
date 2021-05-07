package com.chinafight.gongxiangdaoyou.model.profile;

import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
public class UserModel {
    @NotNull(message = "id不能为空" +
            "")
    private Integer user_id;
    private String user_nick;
    private String user_name;
    private String user_password;
    private String user_avatar;
    private String user_card;
    private Integer user_vip;
    private Integer user_lv;
    private Integer user_power;
    private String user_phone;
    private String user_trueName;
    private Integer user_sex;

    public Integer getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(Integer user_sex) {
        this.user_sex = user_sex;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_trueName() {
        return user_trueName;
    }

    public void setUser_trueName(String user_trueName) {
        this.user_trueName = user_trueName;
    }

    @Override
    public String toString() {
        super.toString();
        return "UserModel{" +
                "user_id=" + user_id +
                ", user_nick='" + user_nick + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_avatar='" + user_avatar + '\'' +
                ", user_card=" + user_card +
                ", user_vip=" + user_vip +
                ", user_lv=" + user_lv +
                ", user_power=" + user_power +
                ", user_phone=" + user_phone +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_card() {
        return user_card;
    }

    public void setUser_card(String user_card) {
        this.user_card = user_card;
    }

    public Integer getUser_vip() {
        return user_vip;
    }

    public void setUser_vip(Integer user_vip) {
        this.user_vip = user_vip;
    }

    public Integer getUser_lv() {
        return user_lv;
    }

    public void setUser_lv(Integer user_lv) {
        this.user_lv = user_lv;
    }

    public Integer getUser_power() {
        return user_power;
    }

    public void setUser_power(Integer user_power) {
        this.user_power = user_power;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
