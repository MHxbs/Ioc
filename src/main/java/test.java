
import org.redrock.ioc.javabean.Student;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class test {
        public static void main(String[] args) {
          Student student=new Student();
          Class clazz=student.getClass();
          Method[] methods=clazz.getDeclaredMethods();

            List<Object> as=new ArrayList<Object>();
            as.add("asd");
          for (Method method:methods){
              if (method.getName().matches("setName")){
                  Type[] parameterTypes=method.getGenericParameterTypes();
                  try {
                      method.invoke(student,as.toArray());
                  } catch (IllegalAccessException e) {
                      e.printStackTrace();
                  } catch (InvocationTargetException e) {
                      e.printStackTrace();
                  }
              }
          }
            System.out.println(student.getName());
        }
}




