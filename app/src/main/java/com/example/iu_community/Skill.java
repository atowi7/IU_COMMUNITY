package com.example.iu_community;

public class Skill {
    String id;
    String title;
    String rate;
    String attachimgurl;
    String userid;

    public Skill() {
    }

    public Skill(String id,String title, String rate, String attachimgurl, String userid) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.attachimgurl = attachimgurl;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAttachimgurl() {
        return attachimgurl;
    }

    public void setAttachimgurl(String attachimgurl) {
        this.attachimgurl = attachimgurl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
