package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.dto.CommentDTO;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.mapper.discover.CommentMapper;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import com.chinafight.gongxiangdaoyou.model.discover.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    public CommentDTO insertComment(Long commenter,String text,Long questionId){
        UserModel user = userMapper.findUserById(Math.toIntExact(commenter));
        Comment comment = new Comment();
        comment.setContent(text);
        comment.setParent_id(questionId);
        comment.setCommentator(commenter);
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setGmt_create(System.currentTimeMillis());
        commentMapper.insertComment(comment);
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        commentDTO.setUser(user);
        return commentDTO;
    }
}
