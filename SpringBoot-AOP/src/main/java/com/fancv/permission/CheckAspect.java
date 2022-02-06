package com.fancv.permission;

import com.fancv.ExceptionHandler.BizException;
import com.fancv.login.UserContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class CheckAspect {

    @Around(value = "@annotation(com.fancv.permission.PermissionAnnotation)")
    public Object checkUser(ProceedingJoinPoint pjp) throws Throwable {


        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        Class returnType = methodSignature.getReturnType();
        PermissionAnnotation permissionAnnotation = method.getAnnotation(PermissionAnnotation.class);
        String s = permissionAnnotation.permission();
        //获取参数
        Object[] args = pjp.getArgs();
        System.out.println("在进入方法之前 校验权限： " + s);

        List<String> ps = UserContext.getUser().getPermissions();
        System.out.println("登录用户已经有的权限" + ps.toArray());
        if (ps.contains(s)) {
            System.out.println("用户拥有接口权限");
        } else {
            throw new BizException("420","用户没有对应权限查询");
        }
        //放行
        Object proceed = pjp.proceed();
        System.out.println("执行完方法之后");
        return proceed;
    }

    @Before("execution(* com.fancv.controller..*.*(..))")
    public void before() {
        System.out.println("在方法执行之前执行");
    }
}
