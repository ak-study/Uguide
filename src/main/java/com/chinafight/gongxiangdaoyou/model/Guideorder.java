package com.chinafight.gongxiangdaoyou.model;

import java.io.Serializable;

public class Guideorder implements Serializable {
    private Integer orderid;

    private Integer orderuser;

    private Integer orderguide;

    private String orderprice;

    private String orderfrom;

    private String orderdst;

    private String opinion;

    private String orderstatus;

    private String detailedlocation;

    private static final long serialVersionUID = 1L;

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getOrderuser() {
        return orderuser;
    }

    public void setOrderuser(Integer orderuser) {
        this.orderuser = orderuser;
    }

    public Integer getOrderguide() {
        return orderguide;
    }

    public void setOrderguide(Integer orderguide) {
        this.orderguide = orderguide;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice == null ? null : orderprice.trim();
    }

    public String getOrderfrom() {
        return orderfrom;
    }

    public void setOrderfrom(String orderfrom) {
        this.orderfrom = orderfrom == null ? null : orderfrom.trim();
    }

    public String getOrderdst() {
        return orderdst;
    }

    public void setOrderdst(String orderdst) {
        this.orderdst = orderdst == null ? null : orderdst.trim();
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus == null ? null : orderstatus.trim();
    }

    public String getDetailedlocation() {
        return detailedlocation;
    }

    public void setDetailedlocation(String detailedlocation) {
        this.detailedlocation = detailedlocation == null ? null : detailedlocation.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderid=").append(orderid);
        sb.append(", orderuser=").append(orderuser);
        sb.append(", orderguide=").append(orderguide);
        sb.append(", orderprice=").append(orderprice);
        sb.append(", orderfrom=").append(orderfrom);
        sb.append(", orderdst=").append(orderdst);
        sb.append(", opinion=").append(opinion);
        sb.append(", orderstatus=").append(orderstatus);
        sb.append(", detailedlocation=").append(detailedlocation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}