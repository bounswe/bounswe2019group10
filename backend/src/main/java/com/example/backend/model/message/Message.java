package com.example.backend.model.message;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "message_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "conversation_id")
    private int conversationId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "message_time")
    private Timestamp messageTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }
}
