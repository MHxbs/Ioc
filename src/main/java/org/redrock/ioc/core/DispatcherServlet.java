package org.redrock.ioc.core;



import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet("/dispatcher/*")

public class DispatcherServlet extends GenericServlet {
    private Map<Class<?>,Object> controllers;
    private Map<String,Method> handlers;

    // servlet初始化时就创建ClassLoader和BeanFactory
    // 从而进行包扫描
    @Override
    public void init() throws ServletException {
        ClassLoader classLoader=new ClassLoader();
        BeanFactory beanFactory=new BeanFactory(classLoader);
        controllers=beanFactory.getControllers();
        handlers=beanFactory.getHandlers();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        // 得到请求的方法和url,拼接成handleKey,从而得到Method

        String handleKey=request.getMethod()+":"+request.getRequestURI();
        for (Map.Entry<String,Method> entry:handlers.entrySet()){
            System.out.println(entry.getKey());
        }
        Method handler=handlers.get(handleKey);
        // 通过handler得到他所属的类，从而得到controller
        Object controller=controllers.get(handler.getDeclaringClass());
        if (controller!=null){
            try {
                handler.invoke(controller,request,response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}