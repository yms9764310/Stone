package com.jc.myaspect;

import com.jc.mapper.StoreManagementMapper;
import com.jc.mapper.StorePutInMapper;
import com.jc.model.Consants;
import com.jc.model.Store;
import com.jc.model.StoreWarn;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 年: 2019
 * 月: 09
 * 日: 06
 * 小时: 15
 * 分钟: 50
 *
 * @author 严脱兔
 */

@Component
@Aspect
public class MyAspect {
    @Resource(name = "storePutInMapper")
    private StorePutInMapper storePutInMapper;

    @Resource(name = "storeManagementMapper")
    private StoreManagementMapper storeManagementMapper;

   @Pointcut("execution(public String com.jc.serviceImpl.StorePutInServiceImpl.updateCheckOutSuccess(..))")
    public void msg(){

    }

    @AfterReturning(pointcut = "msg()")
    public String  log(){
//        Store store = storePutInMapper.loadByProductId(Consants.PRODUCTID);
//        StoreWarn storeWarn = storeManagementMapper.loadByProduct_id(Consants.PRODUCTID);
//        if(store.getNumber()<storeWarn.getWarn_number()||store.getNumber()==storeWarn.getWarn_number()){
//            //发送站内信
//            return "Acquisition";
//        }
        return "Success";
    }

//    public  void aspectMethod(JoinPoint joinPoint,Object retValue){
//        Object object[] = joinPoint.getArgs(); // 获取被切函数 的参数
//        System.out.println("仓库仓库");
//        System.out.println(object);
//    }

    public String[] getFieldsName(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();

        System.out.println(parameterNames);

        return parameterNames;
    }
}
