package com.daofei.pojo;

import java.util.Date;

public class Comment {
    private int id;
    private int aid;
    private int uid;
    private String content;
    private Date writein;
    private int state;
    private String uimg;

    public String getUimg() {
        return uimg;
    }

    public void setUimg(String uimg) {
        this.uimg = uimg;
    }

    public Date getWritein() {
        return writein;
    }

    public void setWritein(Date writein) {
        this.writein = writein;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
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



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
