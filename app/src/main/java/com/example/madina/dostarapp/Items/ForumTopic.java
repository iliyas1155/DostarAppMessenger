package com.example.madina.dostarapp.Items;

import java.util.Date;

/**
 * Created by The Great on 4/22/2018.
 */

public class ForumTopic {
    public String name;
    public String desc;
    public long createdAt;
    public ForumTopic(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.createdAt = new Date().getTime();
    }
}
