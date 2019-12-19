package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.AdminMapper;
import com.chinafight.gongxiangdaoyou.model.AdminModel;
import com.chinafight.gongxiangdaoyou.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RestController
public class AdminController {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminService adminService;

    @GetMapping("getAdmin")
    public Object getAdmin() {
        HashMap<Object, Object> msgMap = new HashMap<>();
        List<AdminModel> admins = adminService.getAdmin();
        msgMap.put("data", admins);
        msgMap.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return msgMap;
    }

    @GetMapping("deleteAdmin")
    public Object deleteAdmin(int id) {
        Object code = adminService.deleteAdmin(id);
        return code;
    }

    @PostMapping("updateAdmin")
    public Object updateAdmin(AdminModel adminModel) {
        Object code = adminService.updateAdmin(adminModel);
        return code;
    }

    @PostMapping("insertAdmin")
    public Object insertAdmin(AdminModel adminModel) {
        Object code = adminService.insertAdmin(adminModel);
        return code;
    }

    @GetMapping("searchAdmin")
    public Object searchAdmin(AdminModel adminModel) {
        HashMap<Object, Object> msgMap = new HashMap<>();
        AdminModel admin = adminMapper.searchAdmin(adminModel);
        if (admin != null) {
            msgMap.put("admin", admin);
            msgMap.put("status", CustomerEnum.NORMAL_ADMIN_SELECT.getMsgMap());
            return msgMap;
        }
        msgMap.put("status", CustomerEnum.ERROR_NULL_USER.getMsgMap());
        return msgMap;
    }

    @PostMapping("adminLogin")
    public Object adminLogin(AdminModel adminModel, HttpServletRequest request) {
        HashMap<Object, Object> map = new HashMap<>();
        Object loginMsg = adminService.adminLogin(adminModel,request);
        if(loginMsg!=null){
            return loginMsg;
        }
        map.put("status", CustomerEnum.ERROR_LOGIN.getMsgMap());
        return map;
    }


}
