package com.niit.jukebox.database;

public class User {
    private String userName;
    private int pin;
    private int userId;

    public User(String userName, int pin) {
        this.userName = userName;
        this.pin = pin;
    }
    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
