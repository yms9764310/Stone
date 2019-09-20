package com.jc.utils;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class NoPermissionException {

    @ExceptionHandler(UnauthorizedException.class)
    public ModelAndView handleShiroException(Exception ex) {
        System.out.println("权限认证失败,权限不足");
        ModelAndView mv = new ModelAndView("unauthorized.");
        return mv;
    }
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        System.out.println("权限认证失败,权限不足");
        return "权限认证失败,权限不足";
    }
}
