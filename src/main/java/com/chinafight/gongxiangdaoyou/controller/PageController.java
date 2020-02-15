package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.model.ViewModel;
import com.chinafight.gongxiangdaoyou.model.profile.AdminModel;
import com.chinafight.gongxiangdaoyou.model.profile.GuideModel;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.utils.PageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RestController
public class PageController {
    @Autowired
    PageService pageService;

    @GetMapping("admin/page")
    public Object page(Integer pageNum, Integer pageSize) {
        HashMap<Object, Object> msgMap = new HashMap<>();
        PageInfo<AdminModel> page = pageService.adminPage(pageNum, pageSize);
        msgMap.put("data", page.getList());
        msgMap.put("size", page.getPages());
        return msgMap;
    }

    @GetMapping("user/page")
    public Object userPage(Integer pageNum, Integer pageSize){
        HashMap<Object, Object> msgMap = new HashMap<>();
        PageInfo<UserModel> userPage = pageService.userPage(pageNum, pageSize);
        msgMap.put("data", userPage.getList());
        msgMap.put("size", userPage.getPages());
        return msgMap;
    }

    @GetMapping("guide/page")
    public Object guidePage(Integer pageNum,Integer pageSize){
        HashMap<Object, Object> map = new HashMap<>();
        PageInfo<GuideModel> guidePage = pageService.guidePage(pageNum, pageSize);
        map.put("data",guidePage.getList());
        map.put("size",guidePage.getPages());
        return map;
    }

    @GetMapping("view/page")
    public Object viewPage(Integer pageNum, Integer pageSize, HttpServletRequest request){
        HashMap<Object, Object> msgMap = new HashMap<>();
        PageInfo<ViewModel> viewPage = pageService.viewPage(pageNum, pageSize, request);
        if (viewPage==null){
            msgMap.put("status", CustomerEnum.ERROR_STATUS.getMsgMap());
            return msgMap;
        }
        msgMap.put("data", viewPage.getList());
        msgMap.put("size", viewPage.getPages());
        return msgMap;
    }
}
