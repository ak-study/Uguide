package com.chinafight.gongxiangdaoyou.mapper.profile;

import com.chinafight.gongxiangdaoyou.model.profile.AdminModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

        @Select("select * from admin")
        List<AdminModel> selectAdmin();

        @Delete("delete from admin where id = #{id}")
        void deleteAdminById(int id);

        @Insert("insert into admin(username,password,status,create_time,power) " +
                "values(#{userName},#{passWord},#{status},#{create_time},#{power})")
        void insertAdmin(AdminModel adminModel);

        @Update("update admin set username=#{userName},password=#{passWord},status=#{status},power=#{power}" +
                " where id = #{id}")
        void updateAdmin(AdminModel adminModel);

        @Select("select * from admin where username = #{userName}")
        AdminModel selectAdminByUserName(AdminModel adminModel);

        @Select("select * from admin where id= #{id}")
        AdminModel selectAdminById(int id);

        @Select("select * from admin where username=#{userName} and password=#{passWord}")
        AdminModel loginAdmin(AdminModel adminModel);

        @Select("select * from admin where username = #{userName}")
        AdminModel searchAdmin(AdminModel adminModel);
}
