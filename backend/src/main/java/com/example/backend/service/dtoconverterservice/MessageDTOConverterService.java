package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.message.Message;
import com.example.backend.model.message.MessageDTO;

public class MessageDTOConverterService {

    public MessageDTO apply(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setConversationId(message.getConversationId());
        messageDTO.setId(message.getId());
        messageDTO.setMessageText(message.getMessageText());
        messageDTO.setMessageTime(message.getMessageTime());
        return messageDTO;
    }
}
