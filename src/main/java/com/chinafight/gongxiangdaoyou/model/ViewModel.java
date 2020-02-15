package com.chinafight.gongxiangdaoyou.model;

public class ViewModel {
    private Integer view_id;
    private Integer view_price;
    private String view_point;
    private Float view_score;
    private String view_title;
    private String view_img;
    private Integer view_off;//门票售出数量

    public Integer getView_id() {
        return view_id;
    }

    public void setView_id(Integer view_id) {
        this.view_id = view_id;
    }

    public Integer getView_price() {
        return view_price;
    }

    public void setView_price(Integer view_price) {
        this.view_price = view_price;
    }

    public String getView_point() {
        return view_point;
    }

    public void setView_point(String view_point) {
        this.view_point = view_point;
    }

    public Float getView_score() {
        return view_score;
    }

    public void setView_score(Float view_score) {
        this.view_score = view_score;
    }

    public String getView_title() {
        return view_title;
    }

    public void setView_title(String view_title) {
        this.view_title = view_title;
    }

    public String getView_img() {
        return view_img;
    }

    public void setView_img(String view_img) {
        this.view_img = view_img;
    }

    public Integer getView_off() {
        return view_off;
    }

    public void setView_off(Integer view_off) {
        this.view_off = view_off;
    }
}
