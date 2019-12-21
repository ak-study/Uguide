package com.chinafight.gongxiangdaoyou.eunm;


import java.util.HashMap;

public enum CustomerEnum {
    NORMAL_STATUS("状态正常","200"),
    NORMAL_ADMIN_LOGIN("登陆成功","200"),
    NORMAL_ADMIN_INSERT("添加成功","200"),
    NORMAL_ADMIN_DELETE("删除成功","200"),
    NORMAL_ADMIN_UPDATE("更新成功","200"),
    NORMAL_ADMIN_SELECT("已搜索到相关用户","200"),
    NORMAL_USER_EXIT("退出成功","200"),
    ERROR_STATUS("状态异常","500"),
    ERROR_ADMIN_EXIST("用户已存在","500"),
    ERROR_LOGIN("登陆失败","500"),
    ERROR_TAGS_EXIT("标签已存在","500"),
    ERROR_NULL_POINT("参数存在空值","500"),
    ERROR_NULL_USER("该用户不存在","500"),
    ERROR_ADMIN_FREEZE("该用户已被冻结","500"),
    ERROR_EXCEPTION_USERNAME("用户名参数非法","501"),
    ERROR_EXCEPTION_PASSWORD("密码参数非法","502"),
    ERROR_EXCEPTION_PHONE("电话填写错误","503"),
    ERROR_EXCEPTION_EXIST("该用户名已被注册","504")
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
