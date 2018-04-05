package org.redrock.ioc.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] args;

    private List<Proxy> proxyList = new ArrayList<Proxy>();
    private int proxyIndex = 0;


    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] args, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.args = args;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }


    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object doProxyChain()throws Throwable{
        Object methodResult;
        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else {
            methodResult = methodProxy.invokeSuper(targetObject,args);
        }
        return methodResult;
    }



}