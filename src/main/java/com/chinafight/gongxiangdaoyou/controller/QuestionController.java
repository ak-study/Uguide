package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.model.discover.Question;
import com.chinafight.gongxiangdaoyou.service.discover.PublishService;
import com.chinafight.gongxiangdaoyou.service.discover.QuestionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
@RestController
public class QuestionController {
    @Autowired
    QuestionDTOService questionDTOService;

    @Autowired
    PublishService publishService;
    @GetMapping("getQuestionDTOs")
    public Object getQuestionDTOs(){
        return questionDTOService.getQuestionDTOList();
    }

    @GetMapping("getPersonalQuestions")
    public Object getPersonalQuestions(Long userId){
        return questionDTOService.getQuestionDTOListByPerson(userId);
    }

    @PostMapping("publishQuestion")
    public Object publishQuestion(Long userId,String title,String text){
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(text);
        Object code = publishService.publishQuestion(question, userId);
        if (code==null){
            return CustomerEnum.ERROR_STATUS.getMsgMap();
        }
        return code;
    }

    @GetMapping("getQuestionById")
    public Object getQuestionById(Long questionId){
        HashMap<Object, Object> questionById = questionDTOService.getQuestionById(questionId);
        if(questionById==null){
            return CustomerEnum.ERROR_STATUS.getMsgMap();
        }
        return questionById;
    }
}
