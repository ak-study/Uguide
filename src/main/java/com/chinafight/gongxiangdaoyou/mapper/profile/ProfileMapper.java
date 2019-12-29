package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.ProfileModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProfileMapper {
    @Update("update profile set profile_tag=#{profile_tag},profile_text=#{profile_text}" +
            " where profile_parentid=#{profile_parentId} and profile_type=#{profile_type}")
    void updateProfile(ProfileModel profileModel);

    @Update("update profile set profile_tag=#{userTags} where profile_parent=#{userId} and profile_type=#{type}")
    void  updateTags(String userTags,Integer userId,Integer type);

    @Insert("insert into profile(profile_tag,profile_text,profile_type,profile_parentid)" +
            "values(#{profile_tag},#{profile_text},#{profile_type},#{profile_parentId})")
    void insertProfile(ProfileModel profileModel);

    @Select("select * from profile where profile_parentid=#{userId} and profile_type=1")
    ProfileModel getUserProfile(Integer userId);

    @Select("select * from profile where profile_parentid=#{guideId} and profile_type=2")
    ProfileModel getGuideProfile(Integer guideId);

    @Update("update profile set profile_text=#{text} where profile_parentid=#{userId} and profile_type=#{type}")
    void updateText(String text,Integer userId,Integer type);
}
