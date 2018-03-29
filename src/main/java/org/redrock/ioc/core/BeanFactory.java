package org.redrock.ioc.core;

import org.redrock.ioc.annotation.Autowried;
import org.redrock.ioc.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BeanFactory {
    private Map<Class<?>,Object> controllers;
    private Map<Class<?>,Object> components;
    private Map<String ,Method> handlers;
    private Map<Class<?>,Object> beans;
    private ClassLoader classLoader;
    // 通过构造器传入classLoader初始化几个Map
    public BeanFactory(ClassLoader classLoader){
        this.classLoader=classLoader;
        initComponents();
        initControllerAndHandlers();
       initBeans();
    }

    public Map<Class<?>, Object> getBeans() {
        return beans;
    }

    public Map<Class<?>, Object> getControllers() {
        return controllers;
    }

    public Map<String, Method> getHandlers() {
        return handlers;
    }

    private void initComponents(){
        // 得到包中的所有component
        Set<Class<?>> componentSet=classLoader.getComponentSet();
        components=new HashMap<Class<?>, Object>();
        // 遍历包中的所有component,通过newInstance得到他的实例
        // 然后加入components
        for (Class<?> clazz:componentSet) {
            try {
                Object object=clazz.newInstance();
                components.put(clazz,object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void initBeans(){
        Set<Class<?>> beanSet=classLoader.getBeanSet();
        beans=new HashMap<Class<?>, Object>();
        for (Class<?> clazz:beanSet){
            try {
                Object object=clazz.newInstance();
                Method[] methods=clazz.getDeclaredMethods();
                for (Method method:methods){
                    RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
                    if (requestMapping!=null){
                        String requestUrl=requestMapping.value()+":"+requestMapping.method();
                        handlers.put(requestUrl,method);
                    }
                }
                beans.put(clazz,object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private void initControllerAndHandlers(){
        // 得到包中的所有controller
        Set<Class<?>> controllerSet=classLoader.getControllerSet();
        controllers=new HashMap<Class<?>, Object>();
        handlers=new HashMap<String, Method>();
        // 遍历所有的controller
        for (Class<?> clazz:controllerSet){
            // 得到controller的注解中的uri
            String baseUri=clazz.getAnnotation(RequestMapping.class)!=null? clazz.getAnnotation(RequestMapping.class).value() : "";
            try {
                // 实例一个对象
                Object controller=clazz.newInstance();
                Field[] fields=clazz.getDeclaredFields();
                // 遍历属性
                for (Field field:fields){
                    if (field.getAnnotation(Autowried.class)!=null){
                        Class<?> fieldClazz=field.getType();
                        // 得到对应field的对象
                        Object fieldValue=components.get(fieldClazz);
                        if (!field.isAccessible()){
                            // 如果是私有的，就设置他能访问
                            field.setAccessible(true);
                        }
                        field.set(controller,fieldValue);
                    }
                }
                // 得到该类的所有方法
                Method[] methods=clazz.getDeclaredMethods();
                for (Method method:methods){

                    RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
                    if (requestMapping!=null){
                        String requestUri=requestMapping.method().name()+":"+baseUri+requestMapping.value();
                        handlers.put(requestUri,method);
                    }
                }
                controllers.put(clazz,controller);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
