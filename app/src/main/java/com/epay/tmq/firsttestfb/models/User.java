package com.epay.tmq.firsttestfb.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tmq on 10/01/2017.
 */

@IgnoreExtraProperties
public class User {

    public String uid;
    public String username;
    public String email;


    public User() {
        // Todo
    }

    public User(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
    }

    public String toString() {
        return "User: " + uid + ", " + username + ", " + email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("username", username);
        result.put("email", email);

        return result;
    }
}
