package com.chinafight.gongxiangdaoyou.mapper.utils;

import com.chinafight.gongxiangdaoyou.model.ImgModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

@Mapper
@Repository
public interface ImgMapper {
    @Insert("insert into img(img_type,img,img_name) values(#{img_type},#{img},#{img_name})")
    void insertImg(Integer img_type, String img, String img_name);

    @Select("select * from img where img_name regexp #{imgName} and img_type=#{imgType}")
    List<ImgModel> getImg(String imgName,Integer imgType);
}

