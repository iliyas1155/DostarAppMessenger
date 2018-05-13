package com.example.madina.dostarapp.Items;


import java.util.Date;
import java.util.List;

public class Vacancy {
    public String name;
    public String desc;
    public String ownerEmail;
    public List<String> responding;
    public long createdAt;
    public Vacancy(String ownerEmail, String name, String desc, List<String> responding) {
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.desc = desc;
        this.createdAt = new Date().getTime();
        this.responding = responding;
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
