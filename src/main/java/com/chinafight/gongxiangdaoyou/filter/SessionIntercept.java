package com.chinafight.gongxiangdaoyou.filter;

import com.chinafight.gongxiangdaoyou.service.utils.IPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class SessionIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();

        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        log.info("地址:{}   名称{}   端口{}",servletPath,serverName,serverPort);
        //        String ipAddr = ipService.getIpAddr(request);
//        request.getSession().setAttribute("ip",ipAddr);
//        HashMap<Object, Object> addrName = ipService.getAddrName(ipAddr);
//        request.getSession().setAttribute("city",addrName.get("city"));
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
