package com.example.bu.model;

public class Notifications {
    //these will both be usernames. The to is what user the from is wanting to be friends with
    private String to;
    private String from;
    public Notifications(){

    }

    public Notifications(String usernameTo, String usernameFrom){
        to = usernameTo;
        from = usernameFrom;
    }

    public String getTo(){
        return to;
    }

    public String getFrom(){
        return from;
    }
}
