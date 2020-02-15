package com.chinafight.gongxiangdaoyou.service.utils;

import com.chinafight.gongxiangdaoyou.mapper.ViewMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.AdminMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.GuideMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.model.ViewModel;
import com.chinafight.gongxiangdaoyou.model.profile.AdminModel;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class PageService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GuideMapper guideMapper;

    @Autowired
    ViewMapper viewMapper;

    @Autowired
    IPService ipService;

    public PageInfo<AdminModel> adminPage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);//这行是重点，表示从pageNum页开始，每页pageSize条数据
        List<AdminModel> adminModels = adminMapper.selectAdmin();
        return new PageInfo<>(adminModels);
    }

    public PageInfo<UserModel> userPage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<UserModel> userList = userMapper.getUserList();
        return new PageInfo<>(userList);
    }

    public PageInfo<GuideModel> guidePage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<GuideModel> guideList = guideMapper.getGuideList();
        return new PageInfo<>(guideList);
    }

    public PageInfo<ViewModel> viewPage(Integer pageNum,Integer pageSize,HttpServletRequest request){
        PageHelper.startPage(pageNum,pageSize);
        String ipAddr = ipService.getIpAddr(request);
        try {
            String city = ipService.getAddrName(ipAddr);
            List<ViewModel> localView = viewMapper.getLocalView(city);
            return new PageInfo<>(localView);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
