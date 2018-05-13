package com.example.madina.dostarapp.Items;

import java.util.Date;

public class Course {
    public String name;
    public String desc;
    public String url;
    public long createdAt;
    public Course(String name, String desc, String url) {
        this.name = name;
        this.desc = desc;
        this.url = url;
        this.createdAt = new Date().getTime();
    }
}
