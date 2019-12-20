package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideMapper;
import com.chinafight.gongxiangdaoyou.model.GuideModel;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GuideService {
    @Autowired
    GuideMapper guideMapper;

    @Value("${avatar}")
    String avatar;
    public Object deleteGuide(Integer guideId){
        if(guideId==null){
            return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        GuideModel guideModel = new GuideModel();
        guideModel.setGuide_id(guideId);
        guideMapper.deleteGuideById(guideModel);
        return CustomerEnum.NORMAL_ADMIN_DELETE.getMsgMap();
    }

    public Object insertGuide(String guideName,String guidePhone,
                              String guidePassWord,String guideTrueName){
        HashMap<Object, Object> paraMsg = Utils.isTrue(guideName, guidePassWord, guidePhone);
        if (paraMsg.size()>0){
            return paraMsg;
        }
        HashMap<Object, Object> map = new HashMap<>();
        GuideModel guide = new GuideModel();
        String nick="新导游"+System.currentTimeMillis();
        guide.setGuide_name(guideName);
        GuideModel tempGuide = guideMapper.getGuideByUserName(guide);
        if(tempGuide!=null){
            map.put("status",CustomerEnum.ERROR_ADMIN_EXIST.getMsgMap());
            return map;
        }
        //默认值
        guide.setGuide_lv(1);
        guide.setGuide_like(0);
        guide.setGuide_avatar(avatar);
        guide.setGuide_vip(0);
        guide.setGuide_power(1);
        guide.setGuide_nick(nick);
        guide.setGuide_card("");
        //前端传入
        guide.setGuide_phone(guidePhone);
        guide.setGuide_password(guidePassWord);
        guide.setGuide_trueName(guideTrueName);
        guideMapper.insertGuide(guide);
        GuideModel temp = guideMapper.getGuideByUserName(guide);
        map.put("status",CustomerEnum.NORMAL_ADMIN_INSERT.getMsgMap());
        map.put("data",temp);
        return map;
    }
}
