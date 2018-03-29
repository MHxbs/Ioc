package org.redrock.ioc.javabean;

import org.redrock.ioc.annotation.Bean;
import org.redrock.ioc.annotation.Component;

@Component
public class Student {
    private String name;
    private int age;
    private String gender;
    private int stu_num;
    private String address;
    private String mail;

    /*public void setNameAndAge(String name,int age){
        this.age=age;
        this.name=name;
    }*/
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public int getStu_num() {
        return stu_num;
    }

    public void setStu_num(int stu_num) {
        this.stu_num = stu_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}
