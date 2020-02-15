package com.chinafight.gongxiangdaoyou.model.profile;

import java.util.Date;

public class AdminModel {

    private Integer id;
    private String userName;
    private String passWord;
    private String status;
    private Long create_time;
    private Integer power;

    @Override
    public String toString() {
        return "AdminModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", status='" + status + '\'' +
                ", create_time=" + create_time +
                ", power=" + power +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
