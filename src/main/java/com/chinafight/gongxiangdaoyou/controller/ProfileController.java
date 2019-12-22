package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.service.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @PostMapping("insertProfile")
    public Object setProfileText(String userTags,String userText,Integer type,Integer userId){
        return profileService.insertProfile(userTags, userText, type, userId);
    }

    @PostMapping("updateText")
    public Object updateText(Integer userId,String text,Integer type){
        return profileService.updateText(userId,text,type);
    }
}
