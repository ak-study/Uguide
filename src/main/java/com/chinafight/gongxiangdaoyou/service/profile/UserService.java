package com.chinafight.gongxiangdaoyou.service.profile;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.ProfileMapper;
import com.chinafight.gongxiangdaoyou.mapper.UserMapper;
import com.chinafight.gongxiangdaoyou.model.ProfileModel;
import com.chinafight.gongxiangdaoyou.model.UserModel;
import com.chinafight.gongxiangdaoyou.utils.CustomerUtils;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    ProfileMapper profileMapper;

    @Value("${avatar}")
    String avatar;
    /**
     * 注册用户
     * @param userName
     * @param userPassWord
     * @param userPhone
     * @return
     */
    public Object insertUser(String userName, String userPassWord,
                             String userPhone){
        HashMap<Object, Object> map = new HashMap<>();
        HashMap<Object, Object> paraMsg = Utils.isTrue(userName, userPassWord, userPhone);
        if(paraMsg.size()>0){
            return paraMsg;
        }
        String userNick="新用户"+System.currentTimeMillis();
        UserModel user = new UserModel();
        user.setUser_name(userName);
        UserModel tempUser = userMapper.getUserByUserName(user);
        if(tempUser!=null){
            map.put("status",CustomerEnum.ERROR_EXCEPTION_EXIST.getMsgMap());
            return map;
        }
        //默认值
        user.setUser_nick(userNick);
        user.setUser_power(1);
        user.setUser_lv(1);
        user.setUser_vip(0);
        user.setUser_avatar(this.avatar);
        user.setUser_trueName("");
        //前端数据
        user.setUser_password(userPassWord);
        user.setUser_card("");
        user.setUser_phone(userPhone);
        userMapper.insertUser(user);
        UserModel userByUserName = userMapper.getUserByUserName(user);
        map.put("data",userByUserName);
        map.put("status",CustomerEnum.NORMAL_ADMIN_INSERT.getMsgMap());
        return map;
    }

    public Object deleteUser(Integer userId){
        if(userId==null){
            return CustomerEnum.NORMAL_ADMIN_DELETE.getMsgMap();
        }
        UserModel user = new UserModel();
        user.setUser_id(userId);
        UserModel tempUser = userMapper.getUserById(user);
        if(tempUser==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        userMapper.deleteUserById(user);
        return CustomerEnum.NORMAL_ADMIN_DELETE.getMsgMap();
    }

    public Object updateUser(String userNick,String userPassWord,String userAvatar,Integer userSex,
                             String userCard,String userPhone,Integer userId,String userTrueName){
        if(userNick==null ||userCard==null||userPhone==null||userSex==null||
        userNick==""||userId==null){
            return  CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        UserModel user = new UserModel();
        user.setUser_id(userId);
        UserModel tmpUser = userMapper.getUserById(user);
        if(tmpUser==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        user.setUser_sex(userSex);
        user.setUser_trueName(userTrueName);
        user.setUser_nick(userNick);
        user.setUser_password(userPassWord);
        user.setUser_avatar(userAvatar);
        user.setUser_card(userCard);
        user.setUser_phone(userPhone);
        userMapper.updateUser(user);
        return CustomerEnum.NORMAL_ADMIN_UPDATE.getMsgMap();
    }

    public Object searchUser(String userName){
        List<UserModel> users = userMapper.searchUserByUserName(userName);
        if(users.size()>0){
            return users;
        }
        return null;
    }

    public Object userLogin(String userName, String userPassWord, HttpServletRequest request){
        UserModel userModel = userMapper.userLogin(userName, userPassWord);
        HashMap<Object, Object> map = new HashMap<>();
        if(userModel!=null){
            HashMap<Object, Object> login = CustomerUtils.login(userModel, null);
            if(login==null){
                map.put("status",CustomerEnum.ERROR_ADMIN_FREEZE.getMsgMap());
                return map;
            }else if(login.size()>0){
                return login;
            }
        }
        map.put("status",CustomerEnum.ERROR_LOGIN.getMsgMap());
        return map;
    }

    public Object userSignOut(String userName){
        UserModel key = Utils.userLoginMap.get(userName);
        if(key!=null){
            Utils.userLoginMap.remove(userName);
        }else{
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        return CustomerEnum.NORMAL_USER_EXIT.getMsgMap();
    }

    public  Object updateUserAvatar(String userAvatar,Integer userId){
        UserModel userModel = new UserModel();
        userModel.setUser_id(userId);
        UserModel tempUser = userMapper.getUserById(userModel);
        if(tempUser==null){
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        userMapper.updateUserAvatar(userAvatar,userId);
        return CustomerEnum.NORMAL_ADMIN_UPDATE.getMsgMap();
    }

}