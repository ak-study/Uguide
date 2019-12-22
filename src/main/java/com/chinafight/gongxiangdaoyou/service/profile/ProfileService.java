package com.chinafight.gongxiangdaoyou.service.profile;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.GuideMapper;
import com.chinafight.gongxiangdaoyou.mapper.ProfileMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.GuideModel;
import com.chinafight.gongxiangdaoyou.model.ProfileModel;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    ProfileMapper profileMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    GuideMapper guideMapper;

    public HashMap<Object, Object> getUserProfile(Integer userId){
        HashMap<Object, Object> map = new HashMap<>();
        UserModel userModel = new UserModel();
        userModel.setUser_id(userId);
        UserModel tempUser = userMapper.getUserById(userModel);
        if(tempUser==null){
            return null;
        }
        ProfileModel userProfile = profileMapper.getUserProfile(userId);
        return getProfileMap(map, userProfile);
    }

    public HashMap<Object,Object> getGuideProfile(Integer guideId){
        HashMap<Object, Object> map = new HashMap<>();
        GuideModel guideModel = new GuideModel();
        guideModel.setGuide_id(guideId);
        if(guideMapper.getGuideById(guideModel)==null){
            return null;
        }
        ProfileModel guideProfile = profileMapper.getGuideProfile(guideId);
        return getProfileMap(map, guideProfile);
    }

    private HashMap<Object, Object> getProfileMap(HashMap<Object, Object> map, ProfileModel profileModel) {
        if (profileModel==null){
            return null;
        }
        String tags = profileModel.getProfile_tag();
        String text = profileModel.getProfile_text();
        String[] split = tags.split(",");
        List<String> tagList = new ArrayList<>(Arrays.asList(split));
        map.put("tags", tagList);
        map.put("text", text);
        return map;
    }

    public Object insertProfile(String userTags,String userText,Integer type,Integer userId){
        UserModel userModel = new UserModel();
        GuideModel guideModel=new GuideModel();
        ProfileModel profileModel = new ProfileModel();
        profileModel.setProfile_parentId(userId);
        profileModel.setProfile_tag(userTags);
        profileModel.setProfile_text(userText);
        profileModel.setProfile_type(type);
        userModel.setUser_id(userId);
        guideModel.setGuide_id(userId);
        if (type==1){
            if (userMapper.getUserById(userModel)==null){
                return CustomerEnum.ERROR_NULL_USER.getMsgMap();
            }
        }else if (type==2){
            if (guideMapper.getGuideById(guideModel)==null){
                return CustomerEnum.ERROR_NULL_USER.getMsgMap();
            }
        }
        //如果数据库中已存在标签，则更新
        if(profileMapper.getGuideProfile(userId)!=null || profileMapper.getUserProfile(userId)!=null){
            profileMapper.updateProfile(profileModel);
            return CustomerEnum.NORMAL_ADMIN_UPDATE.getMsgMap();
        }
        profileMapper.insertProfile(profileModel);
        return CustomerEnum.NORMAL_ADMIN_INSERT.getMsgMap();
    }

    public Object updateText(Integer userId,String text,Integer type){
        UserModel userModel = new UserModel();
        GuideModel guideModel = new GuideModel();
        guideModel.setGuide_id(userId);
        userModel.setUser_id(userId);
        if(userMapper.getUserById(userModel)==null || guideMapper.getGuideById(guideModel)==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        profileMapper.updateText(text,userId,type);
        return CustomerEnum.NORMAL_ADMIN_UPDATE.getMsgMap();
    }

    public Object updateTags(Integer userId,String tags,Integer type){
        UserModel user = userMapper.findUserById(userId);
        if (user==null){
            return null;
        }
        return null;
    }
}
