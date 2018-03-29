package org.redrock.ioc.controller;

import org.redrock.ioc.annotation.Controller;
import org.redrock.ioc.annotation.RequestMapping;
import org.redrock.ioc.annotation.RequestMethod;
import org.redrock.ioc.javabean.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
@Controller
public class StudentControllerByField {
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // 通过field来设置属性的值
    @RequestMapping(value = "/dispatcher/studentByField",method = RequestMethod.POST)
    public void getRequestToBean(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException {

        student =new Student();
        Class clazz=student.getClass();
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
                field.setInt(student, Integer.parseInt(parameterValue));

            }else if (fieldType.equals("class java.lang.String")){// 如果是string类型
                field.set(student,parameterValue);
            }
        }
        System.out.println("name: "+student.getName()+" age: "+student.getAge());
    }

}
