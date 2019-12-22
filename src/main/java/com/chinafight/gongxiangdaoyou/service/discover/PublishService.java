package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.discover.QuestionMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.discover.Question;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PublishService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public Object publishQuestion(Question question,Long userId) {
        if (userId==null){
            return null;
        }
        UserModel user = userMapper.findUserById(Math.toIntExact(userId));
        if(user==null){
            return null;
        }
        UserModel userModel = Utils.userLoginMap.get(user.getUser_name());
        HashMap<Object, Object> map = new HashMap<>();
        //用户登录判断
//        if(userModel==null){
//            map.put("status",CustomerEnum.ERROR_USER_LOGIN.getMsgMap());
//            return null;
//        }
        Object truePublish = isTruePublish(question);
        if(truePublish!=null){
           map.put("status",truePublish);
           return map;
        }
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(userId);
        questionMapper.insertQuestion(question);
        Question questionByID = questionMapper.getQuestionByID(question.getId());
        map.put("status",CustomerEnum.NORMAL_ADMIN_INSERT.getMsgMap());
        map.put("question",questionByID);
        map.put("user",user);
        return map;
    }
    private Object isTruePublish(Question question){
        if (StringUtils.isBlank(question.getTitle())) {
            return CustomerEnum.ERROR_TITLE_NULL.getMsgMap();
        }
        if (StringUtils.isBlank(question.getDescription())) {
            return CustomerEnum.ERROR_TEXT_NULL.getMsgMap();
        }
        return null;
    }
}
