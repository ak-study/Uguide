package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.GuideModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface  GuideMapper {
    @Select("select * from guide")
    List<GuideModel> getGuideList();

    @Select("select * from guide where guide_name=#{guide_name}")
    GuideModel getGuideByUserName(GuideModel guideModel);

    @Select("select * from guide where guide_id=#{guide_id}")
    GuideModel getGuideById(GuideModel guideModel);

    @Delete("delete from guide where guide_id = #{guide_id} ")
    void deleteGuideById(GuideModel guideModel);

    //11个变量
    @Insert("insert into guide(guide_nick,guide_name,guide_password,guide_avatar,guide_card,guide_vip,guide_lv,guide_power,guide_phone,guide_truename,guide_like) " +
            "values(#{guide_nick},#{guide_name},#{guide_password},#{guide_avatar},#{guide_card},#{guide_vip},#{guide_lv},#{guide_power}" +
            ",#{guide_phone},#{guide_trueName},#{guide_like})")
    void insertGuide(GuideModel guideModel);

    //6个变量
    @Update("update guide set guide_nick=#{guide_nick},guide_password=#{guide_password},guide_avatar=#{guide_avatar}," +
            "guide_card=#{guide_card},guide_phone=#{guide_phone},guide_truename=#{guide_trueName} where guide_id=#{guide_id}")
    void updateGuide(GuideModel guideModel);

    @Select("select * from guide where guide_name=#{guideName} and guide_password=#{guide_password}")
    GuideModel guideLogin(GuideModel guideModel);
}
