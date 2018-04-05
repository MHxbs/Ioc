package org.redrock.ioc.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 这个类的作用是代理类加工厂 即我们输入一个需要被代理的目标类和这个目标类被代理的列表，然后输出目标代理类
 */
public class ProxyFactory{

    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
                //这里就是执行方法，只不过是可能会执行一个或多个横切方法
                return new ProxyChain(targetClass,targetObject,targetMethod,methodProxy,args,proxyList).doProxyChain();
            }
        });
    }

}
