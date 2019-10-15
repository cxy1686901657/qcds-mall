package com.qc.mall.interceptor;


import com.alibaba.fastjson.JSON;
import com.qc.mall.annotations.LoginRequired;
import com.qc.mall.util.CookieUtil;
import com.qc.mall.util.NetworkUtil;
import com.qc.mall.utils.HttpclientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qc
 * @date 2019/9/22
 * @description
 * @project mall
 */
@Component
@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURL()+"进入拦截器");
        HandlerMethod hm = (HandlerMethod) handler;
        LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
        // 是否拦截
        if (methodAnnotation == null) {
            return true;
        }
        String token = "";
        String oldToken = CookieUtil.getCookieValue(request, "oldToken", true);

        if(StringUtils.isNotBlank(oldToken)){
            token = oldToken;
        }
        String newToken = request.getParameter("token");
        if(StringUtils.isNotBlank(newToken)){
            token = newToken;
        }
        // 是否必须登录
        boolean loginSuccess = methodAnnotation.loginSuccess();// 获得该请 求是否必登录成功
        // 调用认证中心进行验证
        String success = "fail";
        Map<String,String> successMap = new HashMap<>();
        if(StringUtils.isNotBlank(token)){
            String ip = request.getHeader("x-forwarded-for");// 通过nginx转发的客户端ip
            if(StringUtils.isBlank(ip)){
                ip = request.getRemoteAddr();// 从request中获取ip
                if(StringUtils.isBlank(ip)){
                    ip = "0:0:0:0:0:0:0:1";
                }
            }
            String successJson  = HttpclientUtil.doGet("http://localhost:8005/verify?token=" + token+"&currentIp=127.0.0.1");
//            System.out.println("http://localhost:8005/verify?token=" + token+"&currentIp="+ip);
            try {
                successMap = JSON.parseObject(successJson,Map.class);
                success = successMap.get("status");
            }catch (Exception e){
            }
        }
//        System.out.println(success);
        if(loginSuccess){
            if (!success.equals("success")) {
                StringBuffer requestURL = request.getRequestURL();
                response.sendRedirect("http://localhost:8005/index?ReturnUrl="+requestURL);
                return false;
            }
            // 需要将token携带的用户信息写入
            request.setAttribute("memberId", successMap.get("memberId"));
            request.setAttribute("nickname", successMap.get("nickname"));
            if(StringUtils.isNotBlank(token)){
                CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
            }
        }else{
            // 没有登录也能用，但是必须验证
            if (success.equals("success")) {
                // 需要将token携带的用户信息写入
                request.setAttribute("memberId", successMap.get("memberId"));
                request.setAttribute("nickname", successMap.get("nickname"));
                //验证通过，覆盖cookie中的token
                if(StringUtils.isNotBlank(token)){
                    CookieUtil.setCookie(request,response,"oldToken",token,60*60*2,true);
                }
            }
        }
        return true;
    }
}
