package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.Guideorder;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    List<Guideorder> getOrderByUser(Integer userId);

    List<Guideorder> getOrderByGuide(Integer guideId);

    @Select("select * from guideorder")
    List<Guideorder> getOrderList();
}