package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.mapper.profile.AdminMapper;
import com.chinafight.gongxiangdaoyou.model.profile.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TransactionTest {
    @Autowired
    TransactionTest proxy;

    @Autowired
    AdminMapper adminMapper;

    @GetMapping("websocket")
    public Object test(){
        return "websocket";
    }

    @ResponseBody
    @GetMapping("sent")
    public Object sentMessage() throws IOException {
        return "success";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @GetMapping("test1")
    public Object test1(){
        AdminModel adminModel = new AdminModel();
        adminModel.setUserName("ak");
        adminMapper.insertAdmin(adminModel);
        proxy.test2();
        int n = 1/0;
        return "success";
    }

    public Object test2(){
        AdminModel adminModel = new AdminModel();
        adminModel.setUserName("bk");
        adminMapper.insertAdmin(adminModel);
        return null;
    }

}
