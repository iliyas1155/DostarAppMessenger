package com.example.madina.dostarapp.Items;

import java.util.Date;

public class Course {
    public String name;
    public String category;
    public String desc;
    public String url;
    public long createdAt;
    public Course(String name, String category, String desc, String url) {
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.url = url;
        this.createdAt = new Date().getTime();
    }
}
