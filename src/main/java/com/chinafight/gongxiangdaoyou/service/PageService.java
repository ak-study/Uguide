package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.mapper.AdminMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.AdminModel;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    public PageInfo<AdminModel> adminPage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);//这行是重点，表示从pageNum页开始，每页pageSize条数据
        List<AdminModel> adminModels = adminMapper.selectAdmin();
        PageInfo<AdminModel> pageInfo = new PageInfo<AdminModel>(adminModels);
        return pageInfo;
    }

    public PageInfo<UserModel> userPage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);//这行是重点，表示从pageNum页开始，每页pageSize条数据
        List<UserModel> userList = userMapper.getUserList();
        PageInfo<UserModel> pageInfo = new PageInfo<UserModel>(userList);
        return pageInfo;
    }
}
