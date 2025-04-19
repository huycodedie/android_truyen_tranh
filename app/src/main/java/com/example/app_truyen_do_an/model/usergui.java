package com.example.app_truyen_do_an.model;

public class usergui {
    private String username;
    private String password;

    public usergui(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
