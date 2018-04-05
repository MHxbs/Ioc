package org.redrock.ioc.aop.test;

import org.redrock.ioc.annotation.Aspect;
import org.redrock.ioc.annotation.Component;
import org.redrock.ioc.annotation.Controller;
import org.redrock.ioc.aop.AspectTemplet;

import java.lang.reflect.Method;

@Component
@Aspect(Controller.class)
public class LogAspect extends AspectTemplet {


    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        System.out.println("记录日志开始");
    }

    @Override
    public void after(Class<?> targetClass, Method method, Object[] args, Object result) throws Throwable {
        System.out.println("记录日志结束");
    }
}