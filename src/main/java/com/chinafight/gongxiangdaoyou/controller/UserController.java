package com.chinafight.gongxiangdaoyou.controller;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.ChatMapper;
import com.chinafight.gongxiangdaoyou.mapper.MsgMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.ProfileMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.model.Chat;
import com.chinafight.gongxiangdaoyou.model.Msg;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.service.profile.ProfileService;
import com.chinafight.gongxiangdaoyou.service.profile.UserService;
import com.chinafight.gongxiangdaoyou.socket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@RequestMapping()
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProfileMapper profileMapper;
    @Autowired
    ProfileService profileService;
    @Autowired
    ChatMapper chatMapper;
    @Autowired
    MsgMapper msgMapper;

    @PostMapping("insertUser")
    public Object insertUser(String userName, String userPassWord,
                             String userPhone) {
        Object code = userService.insertUser(userName, userPassWord, userPhone);
        return code;
    }

    @PostMapping("deleteUser")
    public Object deleteUser(Integer userId) {
        Object code = userService.deleteUser(userId);
        return code;
    }

    @PostMapping("updateUser")
    public Object updateUser(String userNick, String userAvatar, Integer userSex,
                             String userCard, String userPhone, Integer userId, String userTrueName) {
        Object code = userService.updateUser(userNick, "",
                userAvatar, userSex, userCard, userPhone, userId, userTrueName);
        return code;
    }

    @GetMapping("getUser")
    public Object getUserList() {
        HashMap<Object, Object> map = new HashMap<>();
        ArrayList<Object> userList = new ArrayList<>();
        for (UserModel userModel : userMapper.getUserList()) {
            HashMap<Object, Object> personal = new HashMap<>();
            HashMap<Object, Object> userProfile = profileService.getUserProfile(userModel.getUser_id());
            userModel.setUser_password("");
            personal.put("user", userModel);
            personal.put("profile", userProfile);
            userList.add(personal);
        }
        map.put("data", userList);
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        return map;
    }

    @GetMapping("searchUser")
    public Object searchUser(String userName) {
        HashMap<Object, Object> map = new HashMap<>();
        Object user = userService.searchUser(userName);
        if (user != null) {
            map.put("data", user);
            map.put("status", CustomerEnum.NORMAL_USER_SELECT.getMsgMap());
            return map;
        }
        return CustomerEnum.ERROR_NULL_USER.getMsgMap();
    }

    @PostMapping("userLogin")
    public Object userLogin(String userName, String userPassWord, HttpServletRequest request) {
        return userService.userLogin(userName, userPassWord, request);
    }

    @GetMapping("socket/sentMsg")
    public Object sentMsg(String msg, String userName) {
        try {
            WebSocketServer.sendInfo(msg, userName);
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
    public Object userSignOut(String userName) {
        Object msg = userService.userSignOut(userName);
        return msg;
    }

    @GetMapping("getUserCount")
    public int getUserCount() {
        return WebSocketServer.getOnlineCount();
    }

    @PostMapping("updateUserAvatar")
    public Object updateUserAvatar(String userAvatar, Integer userId) {
        return userService.updateUserAvatar(userAvatar, userId);
    }

    @PostMapping("getUserById")
    public Object getUserById(Integer userId) {
        HashMap<Object, Object> map = new HashMap<>();
        UserModel userModel = new UserModel();
        userModel.setUser_id(userId);
        HashMap<Object, Object> userProfile = profileService.getUserProfile(userId);
        UserModel user = userMapper.getUserById(userModel);
        if (user == null) {
            map.put("status", CustomerEnum.ERROR_NULL_USER.getMsgMap());
        }
        map.put("status", CustomerEnum.NORMAL_STATUS.getMsgMap());
        map.put("data", user);
        map.put("profile", userProfile);
        return map;
    }

    @PostMapping("updateUserPassWord")
    public Object updateUserPassWord(String newPassWord, Integer userId, String oldPassWord) {
        return userService.updateUserPassWord(userId, newPassWord, oldPassWord);
    }

    @PostMapping("updateUserByAdmin")
    public Object updateUserToAdmin(@Valid UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return Objects.requireNonNull(result.getFieldError()).getDefaultMessage();
        }
        return userService.updateUserToAdmin(userModel);
    }

    @PostMapping("insertChat")
    public Object insertChat(Long userId, Long friendId) {
        Chat chat = new Chat();
        chat.setFriendId(friendId);
        chat.setUserId(userId);
        chatMapper.insert(chat);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    @GetMapping("getChatList")
    public Object getChatList(Long userId) {
        List<Chat> chatModels = chatMapper.selectAllById(userId);
        ArrayList<UserModel> friendList = new ArrayList<>();
        for (Chat chatModel : chatModels) {
            UserModel friend = userMapper.findUserById(Integer.valueOf(chatModel.getFriendId().toString()));
            friendList.add(friend);
        }
        return CustomerEnum.NORMAL_STATUS.getParaMsgMap(friendList);
    }

    @GetMapping("insertMsg")
    public Object insertMsg(Msg msg) {
        Msg firendMsg = new Msg();
        firendMsg.setFriendId(msg.getUserId());
        firendMsg.setUserId(msg.getFriendId());
        firendMsg.setMsg(msg.getMsg());
        msgMapper.insert(msg);
        msgMapper.insert(firendMsg);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    @GetMapping("getMsgByUserId")
    public Object getMsgByUserId(Chat chat){
        return CustomerEnum.NORMAL_STATUS.getParaMsgMap(msgMapper.selectMsgByUserId(chat));
    }

}
