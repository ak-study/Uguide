package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.model.AdminModel;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import com.chinafight.gongxiangdaoyou.service.PageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
