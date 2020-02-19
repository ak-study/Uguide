package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.Guideorder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GuideorderMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(Guideorder record);

    Guideorder selectByPrimaryKey(Integer orderid);

    List<Guideorder> selectAll();

    int updateByPrimaryKey(Guideorder record);
}