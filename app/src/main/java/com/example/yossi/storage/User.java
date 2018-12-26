package com.example.yossi.storage;

public class User {

    String userId;
    String name;
    String profileImageUrl;

    public User(){

    }

    public User(String userId, String name, String profileImageUrl) {
        this.userId = userId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}
