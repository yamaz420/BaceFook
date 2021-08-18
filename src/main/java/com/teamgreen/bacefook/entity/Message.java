package com.teamgreen.bacefook.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass


public class Message {
    private String Head;
    private String body;

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
