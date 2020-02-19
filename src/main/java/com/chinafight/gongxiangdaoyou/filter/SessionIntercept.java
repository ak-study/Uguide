package com.chinafight.gongxiangdaoyou.filter;

import com.chinafight.gongxiangdaoyou.service.utils.IPService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class SessionIntercept implements HandlerInterceptor {
    @Autowired
    IPService ipService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = ipService.getIpAddr(request);
        try {
            HashMap<Object, Object> cityMap = ipService.getAddrName(ipAddr);
            request.getSession().setAttribute("city",cityMap.get("city"));
            request.getSession().setAttribute("province",cityMap.get("province"));
            request.getSession().setAttribute("ip",ipAddr);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
