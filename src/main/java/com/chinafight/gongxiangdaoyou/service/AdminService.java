package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.mapper.AdminMapper;
import com.chinafight.gongxiangdaoyou.model.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    /**
     * 更新管理员，传入id，用户名，密码
     *
     * @param adminModel
     * @return
     */
    public Object updateAdmin(AdminModel adminModel) {
        AdminModel admin = adminMapper.selectAdminById(adminModel.getId());
        if (admin == null) {
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        adminMapper.updateAdmin(adminModel);
        return CustomerEnum.NORMAL_ADMIN_UPDATE.getMsgMap();
    }

    /**
     * 根据管理员id删除管理员
     *
     * @param id
     * @return
     */
    public Object deleteAdmin(int id) {
        if (id == 0) return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        AdminModel adminModel = adminMapper.selectAdminById(id);
        if (adminModel == null) {
            return CustomerEnum.ERROR_NULL_USER.getMsgMap();
        }
        adminMapper.deleteAdminById(id);
        return CustomerEnum.NORMAL_ADMIN_DELETE.getMsgMap();

    }

    /**
     * 传入用户名，密码，权限等级添加管理员
     * 状态默认设置为正常
     *
     * @param adminModel
     * @return
     */
    public Object insertAdmin(AdminModel adminModel) {
        Object aNull = isNull(adminModel);
        if (aNull != null) {
            return aNull;
        }
        AdminModel admin = adminMapper.selectAdminByUserName(adminModel);
        if (admin != null) {
            return CustomerEnum.ERROR_ADMIN_EXIST.getMsgMap();
        }
        adminModel.setCreate_time(System.currentTimeMillis());
        adminModel.setStatus("正常");
        adminMapper.insertAdmin(adminModel);
        return CustomerEnum.NORMAL_ADMIN_INSERT.getMsgMap();
    }

    /**
     * 获取所有管理员
     *
     * @return
     */
    public List<AdminModel> getAdmin() {
        return adminMapper.selectAdmin();
    }

    /**
     * 管理员登陆判断
     *
     * @param adminModel
     * @return
     */
    public Object adminLogin(AdminModel adminModel, HttpServletRequest request) {
        HashMap<Object, Object> map = new HashMap<>();
        UUID token = UUID.randomUUID();
        AdminModel admin = adminMapper.selectAdminByUserName(adminModel);
        String userName = admin.getUserName();
        AdminModel loginAdmin = adminMapper.loginAdmin(adminModel);
        if (admin == null) {
            return null;
        }
        if (!admin.getStatus().equals("正常")) {
            return null;
        }
        if (loginAdmin != null) {
            request.getSession().setAttribute(userName,admin);
            map.put("token",token);
            map.put("status", CustomerEnum.NORMAL_ADMIN_LOGIN.getMsgMap());
            map.put("power",admin.getPower());
            return map;
        }
        return null;
    }

    private Object isNull(AdminModel adminModel) {
        if (adminModel.getUserName() == null ||
                adminModel.getPassWord() == null ||
                adminModel.getUserName() == "" ||
                adminModel.getPassWord() == "") {
            return CustomerEnum.ERROR_NULL_POINT.getMsgMap();
        }
        return null;
    }

}
