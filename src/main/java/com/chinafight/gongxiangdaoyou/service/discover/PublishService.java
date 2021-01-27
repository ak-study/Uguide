package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.discover.QuestionMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.mapper.utils.ImgMapper;
import com.chinafight.gongxiangdaoyou.model.ImgModel;
import com.chinafight.gongxiangdaoyou.model.discover.Question;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class PublishService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionDTOService questionDTOService;
    @Autowired
    ImgMapper imgMapper;

    public Object publishQuestion(Question question, Long userId, MultipartFile[] file) throws IOException {
        if (userId == null) {
            return null;
        }
        UserModel user = userMapper.findUserById(Math.toIntExact(userId));
        if (user == null) {
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
        if (truePublish != null) {
            map.put("status", truePublish);
            return map;
        }
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setCreator(userId);
        questionMapper.insertQuestion(question);

        Question questionByID = questionMapper.getQuestionByID(question.getId());
        if (file!=null) {
            questionDTOService.uploadQuestionImg(questionByID.getId(), file);
            List<ImgModel> img = imgMapper.getImg(questionByID.getId().toString(), 2);
            map.put("img", img);
        }
        map.put("status", CustomerEnum.NORMAL_USER_INSERT.getMsgMap());
        map.put("question", questionByID);
        map.put("user", user);
        return map;
    }

    private Object isTruePublish(Question question) {
        if (StringUtils.isBlank(question.getTitle())) {
            return CustomerEnum.ERROR_TITLE_NULL.getMsgMap();
        }
        if (StringUtils.isBlank(question.getDescription())) {
            return CustomerEnum.ERROR_TEXT_NULL.getMsgMap();
        }
        return null;
    }
}
