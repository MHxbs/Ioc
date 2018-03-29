package org.redrock.ioc.util;

import org.redrock.ioc.javabean.Student;
import org.redrock.ioc.javabean.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class RequestUtil {
    private Student student;
    private User user;
    public static Object getRequestToBean(HttpServletRequest request,Class<?> clazz) throws IllegalAccessException, InstantiationException {
        Object object=null;
        if (clazz.equals(Student.class)){
            object=clazz.newInstance();
        }else if (clazz.equals(User.class)){
            object=clazz.newInstance();
        }else {
            return object;
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
        }
        return object;
    }
}
