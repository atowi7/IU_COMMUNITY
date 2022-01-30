package com.example.iu_community;

public class User {
    String userid;
    String username;
    String Email;
    String pass;
    String userimage;
    String usertype;
    String major;
    String level;

    public User() {
    }

    public User(String userid, String username, String email, String pass, String userimage, String usertype, String major, String level) {
        this.userid = userid;
        this.username = username;
        Email = email;
        this.pass = pass;
        this.userimage = userimage;
        this.usertype = usertype;
        this.major = major;
        this.level = level;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
