package com.example.backend.service.dtoconverterservice;

import com.example.backend.model.message.Conversation;
import com.example.backend.model.message.ConversationDTO;
import com.example.backend.model.message.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConversationDTOConverterService {

    public ConversationDTO apply(Conversation conversation, List<MessageDTO> messageDTOS, String otherusername){
        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setId(conversation.getId());
        conversationDTO.setMember1_id(conversation.getMember1Id());
        conversationDTO.setMember2_id(conversation.getMember2Id());
        conversationDTO.setOtherUsername(otherusername);
        conversationDTO.setMessages(messageDTOS);
        return conversationDTO;
    }
}
