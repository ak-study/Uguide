package com.chinafight.gongxiangdaoyou.service;

import com.chinafight.gongxiangdaoyou.mapper.ViewMapper;
import com.chinafight.gongxiangdaoyou.model.ViewModel;
import com.chinafight.gongxiangdaoyou.service.utils.IPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ViewService {
    @Autowired
    IPService ipService;
    @Autowired
    ViewMapper viewMapper;
}
