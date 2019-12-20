package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.service.UserService;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;


@Controller
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @PostMapping("insertUser")
    public Object insertUser(String userName, String userPassWord,
                             String userPhone) {
        Object code = userService.insertUser(userName, userPassWord, userPhone);
        return code;
    }

    @PostMapping("deleteUser")
    public Object deleteUser(Integer userId){
        Object code = userService.deleteUser(userId);
        return code;
    }

    @PostMapping("updateUser")
    public Object updateUser(String userNick,String userPassWord,String userAvatar,
                             String userCard,Integer userPhone,Integer userId,String userTrueName){
        Object code = userService.updateUser(userNick, userPassWord, userAvatar, userCard, userPhone, userId,userTrueName);
        return code;
    }

    @GetMapping("getUser")
    public Object getUserList(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",userMapper.getUserList());
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    @GetMapping("searchUser")
    public Object searchUser(String userName){
        HashMap<Object, Object> map = new HashMap<>();
        Object user = userService.searchUser(userName);
        if(user!=null){
            map.put("data",user);
            map.put("status", CustomerEnum.NORMAL_ADMIN_SELECT.getMsgMap());
            return map;
        }
        return CustomerEnum.ERROR_NULL_USER.getMsgMap();
    }

    @PostMapping("userLogin")
    public Object userLogin(String userName, String userPassWord, HttpServletRequest request){
        return userService.userLogin(userName, userPassWord,request);
    }

    @GetMapping("socket/sentMsg")
    public Object sentMsg(String msg,String userName)  {
        try {
            WebSocketServer.sendInfo(msg,userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    //页面请求
    @GetMapping("/socket/{cid}")
    public Object socket(@PathVariable String cid) {
        return "登录成功";
    }

    @PostMapping("userSignOut")
    public Object userSignOut(String userName){
        Object msg = userService.userSignOut(userName);
        return msg;
    }
    @GetMapping("getUserCount")
    public int getUserCount(){
        return WebSocketServer.getOnlineCount();
    }
}
