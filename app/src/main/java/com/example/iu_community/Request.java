package com.example.iu_community;

public class Request {
    String id;
    String title;
    String userid;

    public Request() {
    }

    public Request(String id, String title,String userid) {
        this.id = id;
        this.title = title;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
