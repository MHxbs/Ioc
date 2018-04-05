package org.redrock.ioc.aop;


import java.lang.reflect.Method;

public abstract class AspectTemplet implements Proxy{

    public final Object doProxy(ProxyChain proxyChain)throws Throwable{
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] args = proxyChain.getArgs();

        begin();
        try {
            boolean flag = intercept(targetClass,method,args);
            if(flag){
                before(targetClass,method,args);
                result = proxyChain.doProxyChain();
                after(targetClass,method,args,result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            error(targetClass,method,args,e);
            throw e;
        }finally {
            end();
        }
        return result;

    }

    public boolean intercept(Class<?> targetClass, Method method, Object[] args) throws Throwable{
        return true;
    }

    public void begin(){}
    public void before(Class<?> targetClass, Method method,Object[] args) throws Throwable{

    }

    public void after(Class<?> targetClass, Method method, Object[] args, Object result) throws Throwable{

    }

    public void error(Class<?> targetClass, Method method, Object[] args, Throwable e){

    }

    public void end(){}
}