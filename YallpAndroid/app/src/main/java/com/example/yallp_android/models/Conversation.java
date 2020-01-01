package com.example.yallp_android.models;

public class Conversation {
    private int id;
    private int member1_id;
    private int member2_id;
    private Message[] messages;
    private String otherUsername;
    private Boolean read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember1_id() {
        return member1_id;
    }

    public void setMember1_id(int member1_id) {
        this.member1_id = member1_id;
    }

    public int getMember2_id() {
        return member2_id;
    }

    public void setMember2_id(int member2_id) {
        this.member2_id = member2_id;
    }

    public Message[] getMessages() {
        return messages;
    }

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Conversation(int id, int member1_id, int member2_id, Message[] messages, String otherUsername, Boolean read) {
        this.id = id;
        this.member1_id = member1_id;
        this.member2_id = member2_id;
        this.messages = messages;
        this.otherUsername = otherUsername;
        this.read = read;
    }
}
