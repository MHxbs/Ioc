package org.redrock.ioc.controller;

import org.redrock.ioc.annotation.Autowried;
import org.redrock.ioc.annotation.Controller;
import org.redrock.ioc.annotation.RequestMapping;
import org.redrock.ioc.annotation.RequestMethod;
import org.redrock.ioc.javabean.Student;
import org.redrock.ioc.javabean.User;
import org.redrock.ioc.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
@Controller
public class StudentControllerByField {
    @Autowried
    private Student student;
    @Autowried
    private User user;

    public User getUser() {
        return user;
    }


    public Student getStudent() {
        return student;
    }

    // 通过field来设置属性的值
    @RequestMapping(value = "/dispatcher/studentByField",method = RequestMethod.POST)
    public void getRequestToBean(HttpServletRequest request, HttpServletResponse response ) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 得到request传过来的type类型
        String classType=request.getParameter("type");
        classType=classType.substring(0,1).toUpperCase()+classType.substring(1);
        classType="org.redrock.ioc.javabean."+classType;
        if (classType.equals("org.redrock.ioc.javabean.Student")){
            student= (Student) RequestUtil.getRequestToBean(request,Class.forName(classType));
        }else if (classType.equals("org.redrock.ioc.javabean.User")){
            user=(User)RequestUtil.getRequestToBean(request,Class.forName(classType));
        }

       /* Class<?> clazz = null;
        Object object=null;
        if (classType.equals("student")){
            object=student;
            clazz=student.getClass();
        }else if (classType.equals("user")){
            object=user;
            clazz=user.getClass();
        }else {
            return;
        }
        Field[] fields=clazz.getDeclaredFields();
        for (Field field:fields){
            if (!field.isAccessible()){
                field.setAccessible(true);
            }
            // 得到field的名字
            String fieldName=field.getName();
            // 通过fieldName来得到request传来的参数值
            String parameterValue=request.getParameter(fieldName.toString());
            // 得到field的类型
            String fieldType=field.getType().toString();
            // 如果是int类型
            if (fieldType.equals("int")){
                field.setInt(object, Integer.parseInt(parameterValue));

            }else if (fieldType.equals("class java.lang.String")){// 如果是string类型
                field.set(object,parameterValue);
            }
        }*/

        //System.out.println("name: "+student.getName()+" age: "+student.getAge());
        System.out.println("userAge: "+user.getAge()+" userName: " +user.getName());
    }

}
