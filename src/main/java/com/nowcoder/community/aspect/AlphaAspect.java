package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AlphaAspect
{
    //切点
    @Pointcut("execution(* com.nowcoder.community.service.AlphaService.*(..))")
    public void pointcut()
    {
    }

    //连接点前
    @Before("pointcut()")
    public void before()
    {
        System.out.println("before");
    }

    //连接点后
    @After("pointcut()")
    public void after()
    {
        System.out.println("after");
    }

    //返回值后
    @AfterReturning("pointcut()")
    public void afterReturning()
    {
        System.out.println("afterReturning");
    }

    //报错后
    @AfterThrowing("pointcut()")
    public void AfterThrowing()
    {
        System.out.println("AfterThrowing");
    }

    //前后都织入
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable
    {
        System.out.println("around before");

        //调用目标组件
        Object obj = joinPoint.proceed();

        System.out.println("around after");
        return obj;
    }

}
