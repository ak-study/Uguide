package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from user")
    List<UserModel> getUserList();

    @Select("select * from user where user_id= #{user_id}")
    UserModel getUserById(UserModel userModel);

    @Select("select * from user where user_name=#{user_name}")
    UserModel getUserByUserName(UserModel userModel);

    @Insert("insert into user(user_nick,user_name,user_password,user_avatar,user_card,user_vip,user_lv,user_power,user_phone,user_truename) " +
            "values(#{user_nick},#{user_name},#{user_password},#{user_avatar},#{user_card},#{user_vip},#{user_lv},#{user_power}" +
            ",#{user_phone},#{user_trueName})")
    void insertUser(UserModel userModel);

    @Delete("delete from user where user_id = #{user_id}")
    void deleteUserById(UserModel userModel);

    @Update("update user set user_nick=#{user_nick},user_password=#{user_password}," +
            "user_card=#{user_card},user_phone=#{user_phone},user_truename=#{user_trueName} where user_id=#{user_id}")
    void updateUser(UserModel userModel);

    @Select("select * from user where user_name regexp #{userName}")
    List<UserModel> searchUserByUserName(String userName);

    @Select("select * from user where user_name=#{userName} and user_password=#{userPassWord}")
    UserModel userLogin(String userName,String userPassWord);

    @Update("update user set user_avatar=#{user_avatar} where user_id=#{user_id}")
    void  updateUserAvatar(String user_avatar,Integer user_id);
}
