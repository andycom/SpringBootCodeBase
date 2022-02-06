package com.fancv.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = request.getHeader("token");
        log.info("token值：{}", token);
        if (StringUtils.isBlank(token)) {
            log.error("url:{} token is null", request.getRequestURL());
        }

        String url = request.getRequestURI();
        System.out.println("请求地址：" + url);
        try {
            if (url.indexOf("login") > -1) {
                System.out.println("登录接口放行执行登录流程");
                String argues = request.getParameter("userId");
                Integer userId = Integer.getInteger(argues);
                BaseUserDTO baseUserDTO = new BaseUserDTO();
                baseUserDTO.setId(userId);
                baseUserDTO.setUserNmae("test");
                baseUserDTO.setPermissions(new ArrayList<>());
                // 校验权限
                System.out.println("根据userId 查询权限");
                baseUserDTO.addPeemission("1");
                baseUserDTO.addPeemission("2");

                UserContext.setBaseUser(baseUserDTO);
                log.info("登录信息：" + userId);
            } else {
                System.out.println("根据token信息查询用户信息");

                BaseUserDTO baseUserDTO = new BaseUserDTO();
                baseUserDTO.setId(001);
                baseUserDTO.setUserNmae("test");
                baseUserDTO.setPermissions(new ArrayList<>());
                // 校验权限
                System.out.println("根据userId 查询权限");
                baseUserDTO.addPeemission("1");
                baseUserDTO.addPeemission("2");

                UserContext.setBaseUser(baseUserDTO);

            }
        } catch (Exception e) {
            log.error("登录失效，请重新登录！", e);
            throw new Exception("登录失效，请重新登录！", e);
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    /*    UserContext.remove();*/
    }


}