package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.Chat;
import com.chinafight.gongxiangdaoyou.model.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MsgMapper {
    int deleteByPrimaryKey(Integer msgId);

    int insert(Msg record);

    Msg selectByPrimaryKey(Integer msgId);

    List<Msg> selectAll();

    int updateByPrimaryKey(Msg record);

    List<Map<Object,Object>> selectMsgByUserId(Chat chat);

    Map<Object,Object> selectMsgFromLatest(Chat chat);

}