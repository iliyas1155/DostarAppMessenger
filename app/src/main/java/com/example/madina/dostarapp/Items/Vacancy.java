package com.example.madina.dostarapp.Items;


import java.util.Date;

public class Vacancy {
    public String name;
    public String desc;
    public String contacts;
    public long createdAt;
    public Vacancy(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.createdAt = new Date().getTime();
    }

    public void setContacts(String contacts){
        this.contacts = contacts;
    }
}
