package com.chinafight.gongxiangdaoyou.service.profile;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.utils.CustomerUtils;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuideService {
    @Autowired
    GuideMapper guideMapper;

    public Object deleteGuide(Integer guideId){
        if(guideId==null){
            return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        GuideModel guideModel = new GuideModel();
        guideModel.setGuide_id(guideId);
        guideMapper.deleteGuideById(guideModel);
        return CustomerEnum.NORMAL_USER_DELETE.getMsgMap();
    }

    public Object insertGuide(String guideName,String guidePhone,
                              String guidePassWord,String guideTrueName){
        HashMap<Object, Object> paraMsg = Utils.isTrue(guideName, guidePassWord, guidePhone);
        if (paraMsg!=null){
            return paraMsg;
        }
        HashMap<Object, Object> map = new HashMap<>();
        GuideModel guide = new GuideModel();
        String nick="新导游"+System.currentTimeMillis();
        guide.setGuide_name(guideName);
        GuideModel tempGuide = guideMapper.getGuideByUserName(guide);
        if(tempGuide!=null){
            map.put("status",CustomerEnum.ERROR_USER_EXIST.getMsgMap());
            return map;
        }
        //默认值
        guide.setGuide_lv(1);
        guide.setGuide_like(0);
        guide.setGuide_avatar("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1283089748,1187111904&fm=26&gp=0.jpg");
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
        map.put("status",CustomerEnum.NORMAL_USER_INSERT.getMsgMap());
        map.put("data",temp);
        return map;
    }

    public List<GuideModel> searchGuideByName(String guideName){
        List<GuideModel> guideList = guideMapper.searchGuideByUserName(guideName);
        if(guideList!=null){
            return guideList;
        }
        return null;
    }

    public Object guideLogin(String guideName,String guidePassWord){
        GuideModel guide = new GuideModel();
        HashMap<Object, Object> map = new HashMap<>();
        guide.setGuide_password(guidePassWord);
        guide.setGuide_name(guideName);
        GuideModel guideModel = guideMapper.guideLogin(guide);
        if(guideModel!=null){
            HashMap<Object, Object> login = CustomerUtils.login(null, guideModel);
            if(login==null){
                map.put("status",CustomerEnum.ERROR_USER_FREEZE.getMsgMap());
            }else if(login.size()>0){
                return login;
            }
        }
        map.put("status",CustomerEnum.ERROR_LOGIN.getMsgMap());
        return map;
    }

    public Object guideSignOut(String userName){
        GuideModel key = Utils.guideLoginMap.get(userName);
        if(key!=null){
            Utils.guideLoginMap.remove(userName);
        }else{
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        return CustomerEnum.NORMAL_USER_EXIT.getMsgMap();
    }

    public Object updateGuide(String guideNick,String guidePassWord,String guideAvatar, String guideCard
            ,String guidePhone,Integer guideId,String guideTrueName,Integer guideSex){
        if(guideNick==null ||guideCard==null||guidePhone==null||
                guideId==null||guideTrueName==null|| guideNick==""||
                guideCard==""||guidePhone==""||guideTrueName==""){
            return  CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        GuideModel guide = new GuideModel();
        guide.setGuide_id(guideId);
        GuideModel tempGuide = guideMapper.getGuideById(guide);
        if(tempGuide==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        guide.setGuide_sex(guideSex);
        guide.setGuide_trueName(guideTrueName);
        guide.setGuide_nick(guideNick);
        guide.setGuide_password(guidePassWord);
        guide.setGuide_avatar(guideAvatar);
        guide.setGuide_card(guideCard);
        guide.setGuide_phone(guidePhone);
        guideMapper.updateGuide(guide);
        return CustomerEnum.NORMAL_USER_UPDATE.getMsgMap();
    }

    public Object updateGuideAvatar(String guideAvatar,Integer guideId){
        GuideModel guideModel = new GuideModel();
        guideModel.setGuide_id(guideId);
        GuideModel tempGuide = guideMapper.getGuideById(guideModel);
        if(tempGuide==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        guideMapper.updateGuideAvatar(guideAvatar,guideId);
        return CustomerEnum.NORMAL_USER_UPDATE.getMsgMap();
    }

    public Object goodGuide(){
        List<GuideModel> guideList = guideMapper.getGuideList();
        HashMap<Object, Object> map = new HashMap<>();
        Collections.sort(guideList);
        List<GuideModel> tarGuideList=new ArrayList<>();
        int count=1;
        for (GuideModel guideModel : guideList) {
            if (count>6){
                break;
            }
            tarGuideList.add(guideModel);
            count++;
        }
        map.put("data",tarGuideList);
        map.put("status",CustomerEnum.NORMAL_USER_SELECT.getMsgMap());
        return map;
    }

    public Object updateGuideByAdmin(GuideModel guideModel){
        guideMapper.updateGuide(guideModel);
        guideMapper.updateGuideAvatar(guideModel.getGuide_avatar(),guideModel.getGuide_id());
        guideMapper.updateGuidePassword(guideModel);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();

    }
}
