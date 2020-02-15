package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.service.discover.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("insertComment")
    public Object insertComment(Long commenter,String text,Long questionId){
        return commentService.insertComment(commenter, text, questionId);
    }
}
