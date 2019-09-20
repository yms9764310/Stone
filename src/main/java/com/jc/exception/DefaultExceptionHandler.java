package com.jc.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DefaultExceptionHandler implements HandlerExceptionResolver{


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("==============异常开始=============");
        //如果是shiro无权操作，因为shiro 在操作auno等一部分不进行转发至无权限url
        if(ex instanceof UnauthorizedException){
//            ModelAndView mv = new ModelAndView("unauthorized");
//            return mv;
            try {
                response.sendRedirect("/admin_pro/layui/unauthorized.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
