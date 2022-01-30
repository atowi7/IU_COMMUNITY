package com.example.iu_community;

public class Chat {
    private String messagetxt;
    private String messageimg;
    private String did;
    private String isdeleted;
    private String sender;
    private String receiver;

    public Chat() {
    }

    public Chat(String messagetxt, String messageimg, String did, String isdeleted, String sender, String receiver) {
        this.messagetxt = messagetxt;
        this.messageimg = messageimg;
        this.did = did;
        this.isdeleted = isdeleted;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessagetxt() {
        return messagetxt;
    }

    public void setMessagetxt(String messagetxt) {
        this.messagetxt = messagetxt;
    }

    public String getMessageimg() {
        return messageimg;
    }

    public void setMessageimg(String messageimg) {
        this.messageimg = messageimg;
    }

    public String getDid() { return did; }

    public void setDid(String did) { this.did = did; }

    public String getIsdeleted() { return isdeleted; }

    public void setIsdeleted(String isdeleted) { this.isdeleted = isdeleted; }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}