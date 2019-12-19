package com.chinafight.gongxiangdaoyou.utils;

import com.chinafight.gongxiangdaoyou.model.UserModel;

import java.io.File;
import java.util.HashMap;

public class Utils {
    public static final HashMap<String, UserModel> userLoginMap=new HashMap<>();

    public static boolean deleteFile(String pathname){
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
        }
        return result;
    }
}
