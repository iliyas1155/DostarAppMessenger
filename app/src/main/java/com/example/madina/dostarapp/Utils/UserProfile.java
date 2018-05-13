package com.example.madina.dostarapp.Utils;

import com.example.madina.dostarapp.R;
import com.example.madina.dostarapp.SampleActivity;

public class UserProfile extends SampleActivity{
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

    public String getResume(){
        String resume = "";
        resume += getString(R.string.full_name).toUpperCase() + ":\n" + name + "\n\n";
        resume += getString(R.string.gender).toUpperCase() + ":\n" + gender+ "\n\n";
        resume += getString(R.string.email).toUpperCase() + ":\n" + email+ "\n\n";
        resume += getString(R.string.phone_number).toUpperCase() + ":\n" + phoneNumber+ "\n\n";
        resume += getString(R.string.address).toUpperCase() + ":\n" + address+ "\n\n";
        resume += getString(R.string.work_experience).toUpperCase() + ":\n" + workExperience+ "\n\n";
        resume += getString(R.string.about).toUpperCase() + ":\n" + aboutYourself+ "\n\n";
        resume += getString(R.string.skills).toUpperCase() + ":\n" + skills+ "\n\n";
        resume += getString(R.string.education).toUpperCase() + ":\n" + education+ "\n\n";
        return resume;
    }
}
