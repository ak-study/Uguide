package com.chinafight.gongxiangdaoyou.mapper.discover;

import com.chinafight.gongxiangdaoyou.model.discover.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,commentator,gmt_create,gmt_modified,like_count,content) " +
            "values(#{parent_id},#{commentator},#{gmt_create},#{gmt_modified},#{like_count},#{content})")
    void insertComment(Comment comment);

    @Select("select * from comment")
    List<Comment> getCommentList();

    @Update("update comment set comment_count=comment_count+1 where id =#{id}")
    void incCommentCount(Long id);

    @Select("select * from comment where id = #{id}")
    Comment getCommentByID(Long id);

    @Delete("delete from comment where id = #{id}")
    void deleteCommentByID(Long id);

    @Update("update comment set comment_count=comment_count-1 where id =#{id}")
    void downCommentCount(Long id);

    @Select("select * from comment where parent_id=#{questionId}")
    List<Comment> gerCommentsByQuestionId(Long questionId);
}

