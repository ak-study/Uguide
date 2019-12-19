package com.chinafight.gongxiangdaoyou.utils;


import java.lang.reflect.Field;

public class NullBeanUtils {
    public static boolean isNull(Object obj) throws IllegalAccessException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) == null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                return true;
            }
        }
        return false;
    }
}
