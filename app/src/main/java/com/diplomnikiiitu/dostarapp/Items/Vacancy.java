package com.diplomnikiiitu.dostarapp.Items;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vacancy implements Parcelable {
    public String name;
    public String category;
    public String region;
    public String city;
    public String desc;
    public String ownerEmail;
    public List<String> responding;
    public long createdAt;
    public Vacancy(String ownerEmail, String name, String category, String region, String city, String desc, List<String> responding) {
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.category = category;
        this.region = region;
        this.city = city;
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

    // Parcelable implementation.

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(region);
        parcel.writeString(city);
        parcel.writeString(desc);
        parcel.writeString(ownerEmail);
        parcel.writeStringList(responding);
        parcel.writeLong(createdAt);
    }

    public static final Parcelable.Creator<Vacancy> CREATOR = new Parcelable.Creator<Vacancy>() {
        public Vacancy createFromParcel(Parcel in) {
            return new Vacancy(in);
        }

        public Vacancy[] newArray(int size) {
            return new Vacancy[size];
        }
    };

    private Vacancy(Parcel parcel) {
        if (responding == null) {
            responding = new ArrayList<>();
        }

        name = parcel.readString();
        category = parcel.readString();
        region = parcel.readString();
        city = parcel.readString();
        desc = parcel.readString();
        ownerEmail = parcel.readString();
        parcel.readStringList(responding);
        createdAt = parcel.readLong();
    }
}
