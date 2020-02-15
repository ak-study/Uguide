package com.chinafight.gongxiangdaoyou.eunm;


import java.util.HashMap;

public enum CustomerEnum {
    NORMAL_STATUS("状态正常","200"),
    NORMAL_USER_LOGIN("登陆成功","200"),
    NORMAL_USER_INSERT("添加成功","200"),
    NORMAL_USER_DELETE("删除成功","200"),
    NORMAL_USER_UPDATE("更新成功","200"),
    NORMAL_USER_SELECT("已搜索到相关用户","200"),
    NORMAL_USER_EXIT("退出成功","200"),
    ERROR_STATUS("状态异常","500"),
    ERROR_USER_EXIST("用户已存在","500"),
    ERROR_LOGIN("登陆失败","500"),
    ERROR_TAGS_EXIT("标签已存在","500"),
    ERROR_NULL_POINT("参数存在空值","500"),
    ERROR_NULL_USER("该用户不存在","500"),
    ERROR_USER_FREEZE("该用户已被冻结","500"),
    ERROR_USER_LOGIN("用户未登录","500"),
    ERROR_EXCEPTION_USERNAME("用户名参数非法","501"),
    ERROR_EXCEPTION_PASSWORD("密码参数非法","502"),
    ERROR_EXCEPTION_PHONE("电话填写错误","503"),
    ERROR_TITLE_NULL("标题不能为空","504"),
    ERROR_TEXT_NULL("问题描述不能为空","505"),
    ERROR_CARD("身份证号码错误","506"),
    ERROR_PASSWORD("密码修改失败","506"),
    ERROR_EXCEPTION_EXIST("该用户名已被注册","504"),
    ERROR_EXCEPTION_ORDER_NULL("订单未创建注册","500")
    ;

    CustomerEnum(String msg, String code){
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
