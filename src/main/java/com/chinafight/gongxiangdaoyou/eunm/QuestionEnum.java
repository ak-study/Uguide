package com.chinafight.gongxiangdaoyou.eunm;

import java.util.HashMap;

public enum  QuestionEnum {
    QUESTION_NOT_EXIT("问题不存在","500")
    ;
    QuestionEnum(String msg, String code){
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
}
