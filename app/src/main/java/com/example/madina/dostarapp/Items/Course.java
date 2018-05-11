package com.example.madina.dostarapp.Items;

import java.util.Date;

public class Course {
    public String name;
    public String desc;
    public String text;
    public long createdAt;
    public Course(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.createdAt = new Date().getTime();
    }

    public void setText(String text){
        this.text = text;
    }
}
