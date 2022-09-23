package com.example.bu.model;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;

public class Post {
    private String creator;
    private String post;
    private Long date;
    public Post(){}

    public Post(String username, String message){
        creator = username;
        post = message;
        date = System.currentTimeMillis();
    }

    public String getCreator(){
        return creator;
    }

    public String getPost(){
        return post;
    }

    public long getDate(){
        return date;
    }

    public String getFormattedDate(){
        String pattern = " MMM dd, yyyy HH:mm:ss ";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}
