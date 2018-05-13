package com.example.madina.dostarapp.Utils;

public class UserProfile {
    public final String id;
    public final String email;
    public String name;
    public String phoneNumber;
    public String workExperience;
    public String aboutYourself;
    public String address;
    public String skills;
    public String education;
    public String gender;

    public UserProfile(String id, String email){
        this.id = id;
        this.email = email;
    }
}
