package com.chinafight.gongxiangdaoyou.eunm;

import java.util.HashMap;

public enum OrderEnum {
    QUESTION_NOT_EXIT("问题不存在","500"),
    ORDER_TIME_OVER("订单超时","500"),
    ORDER_CANCEL_USER("用户取消订单","500"),
    ORDER_CANCEL_GUIDE("导游无法接单","500"),
    ORDER_CANCEL_GUIDE_2("导游拒绝接单","500"),
    ORDER_OTHER("其他","500"),
    ORDER_EXIT("订单已存在","500")
    ;
    OrderEnum(String msg, String code){
        this.code=code;
        this.msg=msg;
    }
    private String msg;
    private String code;
    private HashMap<Object,Object> msgMap;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public HashMap<Object,Object> getMsgMap(){
        msgMap=new HashMap<>();
        msgMap.put("msg",getMsg());
        msgMap.put("code",getCode());
        return msgMap;
    }
    public HashMap<Object,Object> getMsgMapByPara(Object data){
        msgMap=new HashMap<>();
        msgMap.put("data",data);
        msgMap.put("msg",getMsg());
        msgMap.put("code",getCode());
        return msgMap;
    }
    public HashMap<Object,Object> getMsgMap(Object... msg){
        msgMap=new HashMap<>();
        msgMap.put("msg",msg);
        msgMap.put("code",getCode());
        return msgMap;
    }
}
