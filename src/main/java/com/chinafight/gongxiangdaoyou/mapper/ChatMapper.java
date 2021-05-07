package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.Chat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatMapper {
    int insert(Chat record);

    List<Chat> selectAll();

    List<Chat> selectAllById(Long userId);
}