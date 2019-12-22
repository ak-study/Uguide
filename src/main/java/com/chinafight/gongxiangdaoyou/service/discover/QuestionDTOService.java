package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.dto.QuestionDTO;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.discover.QuestionMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.discover.Question;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionDTOService {
    @Autowired
    QuestionMapper
            questionMapper;
    @Autowired
    UserMapper userMapper;
    /**
     * 获取所有人的问题列表
     * 通过question里面的creator去user表找对应的id，返回一个user
     * 然后通过questionDTO将question和User拼接
     * @return 返回一个questionDTO列表，其中的questionDTO包含了user信息和对应的question信息
     */
    public List<QuestionDTO> getQuestionDTOList(){
        ArrayList<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questionList = questionMapper.getQuestionList();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            Long creator = question.getCreator();
            UserModel userModel=userMapper.findUserById(Math.toIntExact(creator));
            questionDTO.setUserModel(userModel);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }

    /**
     * 获取当前用户的问题列表，封装成questionDTO列表
     * @return 封装了当前用户问题集的列表
     */
    public HashMap<Object, Object> getQuestionDTOListByPerson(Long userId){
        HashMap<Object, Object> map = new HashMap<>();
        if(userId==null){
            map.put("status",CustomerEnum.ERROR_NULL_POINT.getMsgMap());
            return map;
        }
        List<Question> personalQuestions = questionMapper.getQuestionByPerson(userId);
        UserModel user = userMapper.findUserById(Math.toIntExact(userId));
        if(personalQuestions.size()==0 || user==null){
            map.put("status",CustomerEnum.ERROR_NULL_USER.getMsgMap());
            return map;
        }
        map.put("personalQuestions",personalQuestions);
        map.put("creator",user);
        map.put("status", CustomerEnum.NORMAL_ADMIN_SELECT.getMsgMap());
        return map;
    }

    public HashMap<Object, Object> getQuestionById(Long questionId){
        if (questionId==null){
            return null;
        }
        Question questionByID = questionMapper.getQuestionByID(questionId);
        if(questionByID==null){
            return null;
        }
        Long creator = questionByID.getCreator();
        UserModel user = userMapper.findUserById(Math.toIntExact(creator));
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",CustomerEnum.NORMAL_ADMIN_SELECT.getMsgMap());
        map.put("creator",user);
        map.put("question",questionByID);
        return map;

    }
//
//
//    /**
//     *
//     * @param questionDTO 需要查询的问题
//     * @return 与该问题相关联的问题集合（通过tag查询）
//     */
//    public List<QuestionDTO> getRelatedQuestionList(QuestionDTO questionDTO){
//
//    }
}
