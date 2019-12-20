package com.chinafight.gongxiangdaoyou.utils;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.model.UserModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static HashMap<Object, Object> isTrue(String userName,String password,String phone){
        HashMap<Object, Object> map = new HashMap<>();
        boolean passWordMatches = password.matches(".*[a-zA-Z]+.*");
        if(!passWordMatches || password.length()<6 || password.length()>14){
            map.put("status",CustomerEnum.ERROR_EXCEPTION_PASSWORD.getMsgMap());
            return map;
        }
        boolean phoneMatches = phone.matches("[0-9]{11}");
        if(!phoneMatches){
            map.put("status",CustomerEnum.ERROR_EXCEPTION_PHONE.getMsgMap());
            return map;
        }
        if(userName.length()<6|| userName.length()>14){
            map.put("status",CustomerEnum.ERROR_EXCEPTION_USERNAME.getMsgMap());
            return map;
        }
        return map;
    }

    //获取流文件
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
