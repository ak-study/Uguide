package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.dto.CommentDTO;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.discover.QuestionMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.mapper.discover.CommentMapper;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.model.discover.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public HashMap<Object, Object> insertComment(Long commenter,String text,Long questionId){
        UserModel user = userMapper.findUserById(Math.toIntExact(commenter));
        HashMap<Object, Object> map = new HashMap<>();
        if (user==null){
            map.put("status",CustomerEnum.ERROR_NULL_USER.getMsgMap());
            return map;
        }
        Comment comment = new Comment();
        comment.setLike_count(0L);
        comment.setCommentator(commenter);
        comment.setContent(text);
        comment.setParent_id(questionId);
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setGmt_create(System.currentTimeMillis());
        commentMapper.insertComment(comment);
        questionMapper.incCommentCount(questionId);//增加问题的回答人数
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment,commentDTO);
        commentDTO.setUser(user);
        map.put("data",commentDTO);
        map.put("status", CustomerEnum.NORMAL_USER_INSERT.getMsgMap());
        return map;
    }

    public List<Comment> getCommentsByQuestionId(Long questionId){
        return commentMapper.gerCommentsByQuestionId(questionId);
    }


}
