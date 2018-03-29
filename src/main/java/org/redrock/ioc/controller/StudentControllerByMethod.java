package org.redrock.ioc.controller;

import org.redrock.ioc.annotation.Autowried;
import org.redrock.ioc.annotation.Controller;
import org.redrock.ioc.annotation.RequestMapping;
import org.redrock.ioc.annotation.RequestMethod;
import org.redrock.ioc.javabean.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
public class StudentControllerByMethod {

    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



    @RequestMapping(value = "/dispatcher/studentByMethod",method = RequestMethod.POST )
    public void getRequestToBean(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {

        student=new Student();
        Class clazz=student.getClass();
        Method[] methods=clazz.getDeclaredMethods();
        for (Method method:methods){
            // 得到method的名字
            String methodName=method.getName().toString();
            // 如果不是set方法就执行下一个循环
            if (!methodName.matches("set(.*)")){
                continue;
            }

            // 截取methodName的子字符串 如:setName 变成name
            // 然后得到request传来的参数
            String parameterValue=request.getParameter(methodName.substring(3).toLowerCase());
            // 得到method的参数列表的类型
            Type[] methodParameterType=method.getGenericParameterTypes();
            // 参数转型后的List
            List<Object> types=new ArrayList<Object>();
            // 如果没有参数就执行下一循环
            if (methodParameterType.length==0){
                continue;
            }
            // 遍历参数，把参数转为相应的类型
            for (Type type: methodParameterType){
                if (type.toString().equals("int")){
                    types.add(Integer.parseInt(parameterValue));
                }else if (type.toString().equals("class java.lang.String")){
                    types.add(parameterValue);
                }
            }
            method.invoke(student,types.toArray());
        }



       /* while (parameters.hasMoreElements()){
            String parameterName=parameters.nextElement();

            String  parameterValue=request.getParameter(parameterName);
            parameterName=parameterName.substring(0,1).toUpperCase()+parameterName.substring(1);

            System.out.println(parameterName+":"+parameterValue);
            try {
                Method method=clazz.getMethod("set"+parameterName);
                Type[] parameterType=method.getGenericParameterTypes();
                for (Type type:parameterType){
                    if (type.equals("int")){
                        try {
                            method.invoke(student,Integer.parseInt(parameterValue));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }else if (type.equals("class java.lang.String")){
                        method.invoke(student,parameterValue);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("age:"+student.getAge()+"name:"+student.getName());
    }
}
