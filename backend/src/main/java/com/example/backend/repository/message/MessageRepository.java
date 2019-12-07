package com.example.backend.repository.message;

import com.example.backend.model.message.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository {

    List<Message> getAllByConversationId(int conversationId);

}
