package org.redrock.ioc.core;



import com.sun.jndi.toolkit.url.UrlUtil;
import org.redrock.ioc.annotation.Bean;
import org.redrock.ioc.annotation.Component;
import org.redrock.ioc.annotation.Controller;

import java.io.File;
import java.io.FileFilter;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ClassLoader {

    private static final String packageName ="org.redrock.ioc";// 本项目的包名
    private Set<Class<?>> classSet;// 所有文件（controller和component）的Ser
    private Set<Class<?>> controllerSet;
    private Set<Class<?>> componentSet;
    private Set<Class<?>> beanSet;

    public Set<Class<?>> getBeanSet() {
        return beanSet;
    }



    // 通过构造器来进行扫描
    public ClassLoader(){
        load();
    }

    private void load(){
        classSet=new HashSet<Class<?>>();

            try {
                // 得到包的URL
                Enumeration<URL> resources=Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".","/"));


                while (resources.hasMoreElements()){
                    URL resouce=resources.nextElement();
                   // System.out.println(resouce);
                    // 得到包的协议
                    String protocol=resouce.getProtocol();
                    // 如果协议是file
                    if (protocol.equalsIgnoreCase("file")){
                        String packagePath=resouce.getPath();
                        //System.out.println(packagePath);
                        // 加载该路径下的class文件到classSet中
                        loadClass(classSet,packageName,packagePath);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        loadComponentSet();
        loadControllerSet();
        loadBeanSet();
    }
    public Set<Class<?>> getControllerSet() {
        return controllerSet;
    }

    public Set<Class<?>> getComponentSet() {
        return componentSet;
    }
    // 加载packagePath下的所有class文件到ClassSet
    public void loadClass(Set<Class<?>> classSet ,String packageName,String packagePath){
        // 通过listFiles在选择所需要的文件，文件夹和.class文件
        File[] files =new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory()){
                    return true;
                }else {
                    if (pathname.getName().endsWith(".class")){
                        return true;
                    }
                }
                return false;
            }
        });

        if (files!=null&&files.length>0){
            // 遍历文件
            for (File file:files){
                // 得到文件的名字
                String fileName=file.getName();
                // 如果文件是file类型，就加入classSet
                if (file.isFile()){
                    if (packageName!=null&&!packageName.equals("")){
                        fileName=packageName+"."+fileName.substring(0,fileName.lastIndexOf("."));
                    }
                    Class<?> clazz=getClass(fileName);
                    classSet.add(clazz);
                }else {// 如果是文件夹，则用递归函数获得文件
                    String subPackageName=fileName;
                    if (packageName!=null&&!packageName.equals("")){
                        subPackageName=packageName+"."+subPackageName;
                    }
                    String subPackagePath=fileName;
                    if (packagePath!=null&&!packagePath.equals("")){
                        subPackagePath=packagePath+"/"+subPackagePath;
                    }
                    loadClass(classSet,subPackageName,subPackagePath);
                }
            }
        }
    }
    // 加载ComponentSet,从classSet中把注解是Component的加入
    private void loadComponentSet() {
        componentSet = new HashSet<Class<?>>();
        if (classSet != null) {
            for (Class<?> clazz : classSet) {
                if (clazz.getAnnotation(Component.class) != null) {
                    componentSet.add(clazz);
                }
            }
        }
    }
    // 加载ControllerSer,从classSet中把注解是Controller加入
    private void loadControllerSet() {
        controllerSet = new HashSet<Class<?>>();
        if (classSet != null) {
            for (Class<?> clazz : classSet) {
                if (clazz.getAnnotation(Controller.class) != null) {
                    controllerSet.add(clazz);
                }
            }
        }
    }
    private void loadBeanSet(){
        beanSet=new HashSet<Class<?>>();
        if (classSet!=null){
            for (Class<?> clazz:classSet){
                if (clazz.getAnnotation(Bean.class)!=null){
                    beanSet.add(clazz);
                }
            }
        }
    }

    public Set<Class<?>> getClassSet() {
        return classSet;
    }

    // 通过classname返回已给类
    private Class<?> getClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
 }
