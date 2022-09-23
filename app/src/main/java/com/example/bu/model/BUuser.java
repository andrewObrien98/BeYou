package com.example.bu.model;

public class BUuser {
    private String firstName;
    private String lastName;
    private String username;
    private String userID;
    private String email;
    private String date;

    public BUuser(){}

    public BUuser(String first, String last, String user, String date, String id, String email ){
        firstName = first;
        lastName = last;
        username = user;
        userID = id;
        this.email = email;
        this.date = date;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }


    public String getDate() {
        return date;
    }


}
