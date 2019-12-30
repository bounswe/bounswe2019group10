package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.message.Message;
import com.example.backend.model.message.MessageDTO;
import org.springframework.stereotype.Component;

@Component
public class MessageDTOConverterService {

    public MessageDTO apply(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setConversationId(message.getConversationId());
        messageDTO.setId(message.getId());
        messageDTO.setMessageText(message.getMessageText());
        messageDTO.setMessageTime(message.getMessageTime());
        messageDTO.setReceiverUsername(message.getReceiverUsername());
        messageDTO.setSenderUsername(message.getSenderUsername());
        return messageDTO;
    }
}
