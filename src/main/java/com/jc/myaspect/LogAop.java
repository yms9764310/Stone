package com.jc.myaspect;

import com.jc.model.SysLoginUser;
import com.jc.model.UserHistory;
import com.jc.service.UserHistoryService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 年: 2019
 * 月: 09
 * 日: 12
 * 小时: 10
 * 分钟: 26
 *
 * @author 严脱兔
 */
@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserHistoryService userHistoryService;
    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method; //访问的方法


    //前置通知  主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
    //@Before("execution(* com.jc.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = joinPoint.getTarget().getClass();//具体要访问的类
        String methodName = joinPoint.getSignature().getName();//获取访问方法的名称
        Object[] args = joinPoint.getArgs();//获取访问的方法的返回值
        List<Object> list = Arrays.asList(args);

        //获取具体执行的方法的Method对象
        if (list == null || list.size() == 0) {
            method = clazz.getMethod(methodName);//只能获取无参数的方法
        } else {
            Class[] classArgs = new Class[args.length];
            System.out.println(classArgs.length);
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName);
        }
    }

    //后置通知
   //@After("execution(* com.jc.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        long time = new Date().getTime() - visitTime.getTime();//获取访问的时长
        String url = "";
        //获取url    通过反射来操作的
        if (clazz != null && method != null && clazz != LogAop.class) {
            //1.获取类上的 @RequestMapping("/getCheckOut")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                //2.获取方法上的 @RequestMapping(XXX)
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                    //获取访问的IP地址
                    String ip = request.getRemoteAddr();
                    //获取当前用户   Spring Security提供的方法获取
                    //通过securityContext获取，也可以从request.getSession中获取
                    //securityContext方式
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前登录的用户
                    //request.getSession方式
                    //request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

                    User user = (User) context.getAuthentication().getPrincipal();
                    SysLoginUser users = (SysLoginUser) SecurityUtils.getSubject().getPrincipal();
                    String username = user.getUsername();
                    int user_id = users.getId();
                    //将日志相关信息封装到实体类里面
                    UserHistory userHistory = new UserHistory();
                    userHistory.setExecutionTime(time);
                    userHistory.setIp(ip);
                    String operating = "";
                    if (method.getName().contains("insert")) {
                        operating = "添加";
                    }else if(method.getName().contains("delete")) {
                        operating = "删除";
                    }else if(method.getName().contains("update")) {
                        operating = "修改";
                    }else if(method.getName().contains("review")) {
                        operating = "审核";
                    }
                    userHistory.setDescription(operating);
                    userHistory.setUrl(url);
//                    userHistory.setUser_name(username);
                    userHistory.setUser_id(user_id);
                    userHistory.setVisitTime(visitTime);
                    //调用Service
                    userHistoryService.save(userHistory);
                }
            }
        }
    }

}
