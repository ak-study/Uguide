package com.chinafight.gongxiangdaoyou.utils;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.GuideModel;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerUtils {

    public static HashMap<Object, Object> login(UserModel userModel,GuideModel guideModel){
        UUID token=UUID.randomUUID();
        HashMap<Object, Object> map = new HashMap<>();
        if(userModel!=null){
            if(userModel.getUser_power()<1){
                return null;
            }
            map.put("token",token);
            map.put("data",userModel);
            map.put("status",CustomerEnum.NORMAL_ADMIN_LOGIN.getMsgMap());
            Utils.userLoginMap.put(userModel.getUser_name(),userModel);
            return map;
        }
        if(guideModel!=null){
            if(guideModel.getGuide_power()<1){
                return null;
            }
            map.put("token",token);
            map.put("data",guideModel);
            map.put("status",CustomerEnum.NORMAL_ADMIN_LOGIN.getMsgMap());
            Utils.guideLoginMap.put(guideModel.getGuide_name(),guideModel);
        }
        return map;
    }
}
