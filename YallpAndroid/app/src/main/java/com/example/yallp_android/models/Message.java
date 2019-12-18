package com.example.yallp_android.models;

public class Message {
    private int conversationId;
    private int id;
    private String messageText;
    private String messageTime;
    private String receiverUsername;
    private String senderUsername;

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Message(int conversationId, int id, String messageText, String messageTime, String receiverUsername, String senderUsername) {
        this.conversationId = conversationId;
        this.id = id;
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.receiverUsername = receiverUsername;
        this.senderUsername = senderUsername;
    }
}
