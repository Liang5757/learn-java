package com.example.demo;

public class Student {
    private String sno;
    private String name;
    private String birthday;

    public Student(String sno, String name, String birthday) {
        this.sno = sno;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "学号：" + this.getSno() + ";姓名：" + this.getName() + "；生日：" + this.getBirthday();
    }

    String getSno() {
        return this.sno;
    }

    String getName() {
        return this.name;
    }


    String getBirthday() {
        return this.birthday;
    }
}
