package com.example.bu.model;

import java.util.ArrayList;

public class Friends {
    private String username;
    private ArrayList<String> friends = new ArrayList<>();

    public Friends(){}

    public Friends(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void addFriends(String friend) {
        this.friends.add(friend);
    }

    public boolean hasFriend(String friend){
        return friends.contains(friend);
    }
}
