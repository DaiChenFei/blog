package com.daofei.pojo;

import java.util.Date;

public class Message {
    private int id;
    private int uid;
    private String content;
    private Date writein;
    private String uimg;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWritein() {
        return writein;
    }

    public void setWritein(Date writein) {
        this.writein = writein;
    }
}
