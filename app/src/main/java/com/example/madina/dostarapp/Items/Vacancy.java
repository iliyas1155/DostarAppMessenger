package com.example.madina.dostarapp.Items;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vacancy {
    public String name;
    public String desc;
    public String contacts;
    public List<String> responding;
    public long createdAt;
    public Vacancy(String name, String desc, List<String> responding) {
        this.name = name;
        this.desc = desc;
        this.createdAt = new Date().getTime();
        this.responding = responding;
    }

    public void setContacts(String contacts){
        this.contacts = contacts;
    }

    public boolean addResponding(String id){
        if(!responding.contains(id)){
            responding.add(id);
            return true;
        }else{
            return false;
        }
    }
}
