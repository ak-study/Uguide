package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideMapper;
import com.chinafight.gongxiangdaoyou.model.GuideModel;
import com.chinafight.gongxiangdaoyou.service.GuideService;
import org.apache.ibatis.annotations.Insert;
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

    @GetMapping("getGuide")
    public Object getGuideList(){
        HashMap<Object, Object> map = new HashMap<>();
        List<GuideModel> guideList = guideMapper.getGuideList();
        map.put("data",guideList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }


    @GetMapping("deleteGuide")
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
}
