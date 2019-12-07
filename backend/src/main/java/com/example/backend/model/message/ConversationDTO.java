package com.example.backend.model.message;

import java.util.List;

public class ConversationDTO {

    private int id;

    private int member1_id;

    private int member2_id;

    private List<MessageDTO> messages;

    private String otherUsername;

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

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public void setOtherUsername(String otherUsername) {
        this.otherUsername = otherUsername;
    }
}
