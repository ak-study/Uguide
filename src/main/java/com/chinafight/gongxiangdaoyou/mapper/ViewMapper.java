package com.chinafight.gongxiangdaoyou.mapper;

import com.chinafight.gongxiangdaoyou.model.ViewModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ViewMapper {
    @Select("select * from view where view_point regexp #{localPoint}")
    List<ViewModel> getLocalView(String localPoint);

    @Select("select * from view")
    List<ViewModel> getViewList();
}
