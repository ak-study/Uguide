package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideMapper;
import com.chinafight.gongxiangdaoyou.model.GuideModel;
import com.chinafight.gongxiangdaoyou.service.profile.GuideService;
import com.chinafight.gongxiangdaoyou.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;

@Controller
@RestController
public class GuideController {
    @Autowired
    GuideMapper guideMapper;
    @Autowired
    GuideService guideService;
    @Autowired
    ProfileService profileService;

    @GetMapping("getGuide")
    public Object getGuideList(){
        HashMap<Object, Object> map = new HashMap<>();
        List<GuideModel> guideList = guideMapper.getGuideList();
        map.put("data",guideList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }


    @PostMapping("deleteGuide")
    public Object deleteGuide(Integer guideId){
        Object code = guideService.deleteGuide(guideId);
        return code;
    }

    @PostMapping("insertGuide")
    public Object insertGuide(String guideName,String guidePhone,
                              String guidePassWord,String guideTrueName){
        Object code = guideService.insertGuide(guideName,guidePhone,guidePassWord,guideTrueName);
        return code;
    }

    @GetMapping("searchGuide")
    public Object searchGuideList(String guideName){
        HashMap<Object, Object> map = new HashMap<>();
        List<GuideModel> guideList = guideService.searchGuideByName(guideName);
        if (guideList.size()>0){
            map.put("data",guideList);
            map.put("status",CustomerEnum.NORMAL_ADMIN_SELECT.getMsgMap());
            return map;
        }
        map.put("status",CustomerEnum.ERROR_NULL_USER.getMsgMap());
        return map;
    }

    @PostMapping("guideLogin")
    public Object guideLogin(String guideName,String guidePassWord){
        Object code = guideService.guideLogin(guideName, guidePassWord);
        return code;
    }

    @PostMapping("guideSignOut")
    public Object guideSignOut(String guideName){
        return guideService.guideSignOut(guideName);
    }

    @PostMapping("updateGuide")
    public Object updateGuide(String guideNick,String guideAvatar,Integer guideSex,
                              String guideCard,String guidePhone,Integer guideId,String guideTrueName){
        Object code = guideService.updateGuide(guideNick, "", guideAvatar,
                guideCard, guidePhone, guideId, guideTrueName,guideSex);
        return code;
    }

    @PostMapping("updateGuideAvatar")
    public Object updateGuideAvatar (String guideAvatar,Integer guideId){
        return guideService.updateGuideAvatar(guideAvatar,guideId);
    }

    @PostMapping("getGuideById")
    public Object getGuideById(Integer guideId){
        GuideModel guideModel = new GuideModel();
        HashMap<Object, Object> map = new HashMap<>();
        guideModel.setGuide_id(guideId);
        HashMap<Object, Object> guideProfile = profileService.getGuideProfile(guideId);
        GuideModel guide = guideMapper.getGuideById(guideModel);
        if(guide==null){
            map.put("status",CustomerEnum.ERROR_NULL_USER.getMsgMap());
            return map;
        }
        map.put("data", guide);
        map.put("profile", guideProfile);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }
}
