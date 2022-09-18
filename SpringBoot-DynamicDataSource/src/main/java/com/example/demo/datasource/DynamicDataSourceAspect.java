package com.example.demo.datasource;

import com.example.demo.utils.BaseUserDTO;
import com.example.demo.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切面拦截
 *
 * @author 李嘉
 * @version 1.0
 * @Description 动态数据源切面拦截
 * @date 2020/5/19 00:29
 */
@Slf4j
@Component
@Aspect
@Order(1) // 请注意：这里order一定要小于tx:annotation-driven的order，即先执行DynamicDataSourceAspectAdvice切面，再执行事务切面，才能获取到最终的数据源
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicDataSourceAspect {
    //前置通知
    @Before(value="execution(* com.example.demo.service.impl.*.*(..))")
    public void before(){
        System.out.println("前置通知");
    }

    @Around(value = "execution(* com.example.demo.service.impl.*.*(..))")
    public Object  doAround(ProceedingJoinPoint pjp) throws Throwable {
        try {
            BaseUserDTO user = UserContext.getUser();
            Integer r = user.getId();
            String nb = user.getUserNmae();
            DynamicDataSourceContextHolder.setDataSourceKey(nb);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
           // DynamicDataSourceContextHolder.clearDataSourceKey();
        }
        Object proceed = null;
        try {
//			进行方法的执行
            proceed = pjp.proceed();
            System.out.println("方法返回时");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("方法异常时" + e);
        }finally{
            System.out.println("后置方法");
        }

        //将方法执行的返回值返回
        return proceed;
    }

}
