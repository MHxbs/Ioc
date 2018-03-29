
import org.redrock.ioc.javabean.Student;
import org.redrock.ioc.javabean.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class test {
        public static void main(String[] args) {
            Student student= null;
            try {
                student = (Student) Class.forName("org.redrock.ioc.javabean.Student").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class class2=student.getClass();
            System.out.println(class2);
            System.out.println(class2.getClassLoader());
            try {
                Class class1=Class.forName("org.redrock.ioc.javabean.Student");
                System.out.println(class1);
                System.out.println(class1.getClassLoader());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class class3=Student.class;
            System.out.println(class3);
            System.out.println(class3.getClassLoader());

        }
}




