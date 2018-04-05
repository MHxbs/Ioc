package org.redrock.ioc.aop;


import org.redrock.ioc.annotation.Aspect;
import org.redrock.ioc.core.ClassLoader;

import java.lang.annotation.Annotation;
import java.util.*;

public class AopLoader {

    private Set<Class<?>> classSet;
    private Map<Class<?>,Object> targetProxyMap;

    public AopLoader(ClassLoader classLoader){
        this.classSet=classLoader.getClassSet();

        Map<Class<?>,Set<Class<?>>> proxyMap=creatProxyMap();
        Map<Class<?>,List<Proxy>> targetMap= null;
        try {
            targetMap = createTargetMap(proxyMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        targetProxyMap=new HashMap<Class<?>, Object>();
        for (Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet()){
            Class<?> targetClass=targetEntry.getKey();
            List<Proxy> proxyList=targetEntry.getValue();
            Object proxy=ProxyFactory.createProxy(targetClass,proxyList);

            targetProxyMap.put(targetClass,proxy);
        }
    }

    private Map<Class<?>,Set<Class<?>>> creatProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();

        Set<Class<?>> proxyClassSet=getClassSetBySuper(AspectTemplet.class);
        for (Class<?> proxyClass:proxyClassSet){
            if (proxyClass.isAnnotationPresent(Aspect.class)){

                Aspect aspect=proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = null;
                try {
                    targetClassSet = createTargetClassSet(aspect);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * 获取目标类和代理对象列表之间的映射关系
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{

        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>,List<Proxy>>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntry :proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass:targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }

        return targetMap;
    }

    public Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> subClassSet = new HashSet<Class<?>>();
        for (Class<?> clazz : classSet) {
            if(superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)){
                subClassSet.add(clazz);
            }
        }
        return subClassSet;
    }
    /**
     * 获取所有目标类，即@Aspect注解的括号内里 value的值
     * @param aspect
     * @return
     * @throws Exception
     */
    private Set<Class<?>> createTargetClassSet(Aspect aspect)throws Exception{
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取包名下面带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> annoClassSet = new HashSet<Class<?>>();
        for (Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(annotationClass)){
                annoClassSet.add(clazz);
            }
        }
        return annoClassSet;
    }

    public Map<Class<?>, Object> getTargetProxyMap() {
        return targetProxyMap;
    }
}
