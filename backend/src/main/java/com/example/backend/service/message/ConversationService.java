package com.example.backend.service.message;

import com.example.backend.model.member.Member;
import com.example.backend.model.message.*;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.message.ConversationRepository;
import com.example.backend.repository.message.MessageRepository;
import com.example.backend.service.dtoconverterservice.ConversationDTOConverterService;
import com.example.backend.service.dtoconverterservice.MessageDTOConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageDTOConverterService messageDTOConverterService;

    @Autowired
    private ConversationDTOConverterService conversationDTOConverterService;


    public ConversationDTO getById(int conversationId, String username){
        Member member = memberRepository.findByUsername(username);
        Conversation conversation = conversationRepository.getOne(conversationId);
        Member otherMember = memberRepository.getOne(member.getId()==conversation.getMember1Id()?
                                                        conversation.getMember2Id():
                                                        conversation.getMember1Id());
        List<Message> messages = messageRepository.getAllByConversationId(conversationId);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        messages.forEach(message -> messageDTOS.add(messageDTOConverterService.apply(message)));
        return conversationDTOConverterService.apply(conversation, messageDTOS, otherMember.getUsername());
    }

    public MessageDTO addMessage(MessageRequest messageRequest, String memberUsername){
        Member otherMember = memberRepository.findByUsername(messageRequest.getTargetUsername());
        Member member = memberRepository.findByUsername(memberUsername);

        if(otherMember == null){
            return null;
        }

        Conversation conversation = conversationRepository.getAllByTwoMemberIds(member.getId(), otherMember.getId());
        if(conversation == null){
            conversation = new Conversation();
            conversation.setMember1Id(member.getId());
            conversation.setMember2Id(otherMember.getId());
            conversationRepository.save(conversation);
        }

        conversation = conversationRepository.getAllByTwoMemberIds(member.getId(), otherMember.getId());
        Message message = new Message();
        message.setConversationId(conversation.getId());
        message.setMessageText(messageRequest.getMessage());
        messageRepository.save(message);
        return messageDTOConverterService.apply(message);
    }

    public List<ConversationDTO> getAllConversations(String username){
        Member member = memberRepository.findByUsername(username);
        List<Conversation> conversations = conversationRepository.getAllByMemberId(member.getId());
        List<ConversationDTO> conversationDTOS = new ArrayList<>();
        conversations.forEach(conversation -> {
            conversationDTOS.add(getById(conversation.getId(), username));
        });
        return conversationDTOS;
    }

}
