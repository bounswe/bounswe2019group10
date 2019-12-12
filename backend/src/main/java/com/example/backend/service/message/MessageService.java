package com.example.backend.service.message;

import com.example.backend.model.member.Member;
import com.example.backend.model.message.*;
import com.example.backend.model.notification.Notification;
import com.example.backend.model.notification.NotificationType;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.repository.message.ConversationRepository;
import com.example.backend.repository.message.MessageRepository;
import com.example.backend.service.dtoconverterservice.ConversationDTOConverterService;
import com.example.backend.service.dtoconverterservice.MessageDTOConverterService;
import com.example.backend.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class MessageService {

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

    @Autowired
    private NotificationService notificationService;

    public ConversationDTO getById(int conversationId, String username, boolean read){
        Member member = memberRepository.findByUsername(username);
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        if(conversation == null){
            return null;
        }
        Member otherMember = memberRepository.findById(member.getId()==conversation.getMember1Id()?
                                                        conversation.getMember2Id():
                                                        conversation.getMember1Id()).orElse(null);

        if(conversation.getMember2Id()!=member.getId() && conversation.getMember1Id()!=member.getId()){
            //Illegal request. Shouldn't permit the user to see other conversation
            return null;
        }

        List<Message> messages = messageRepository.getAllByConversationId(conversationId);
        List<MessageDTO> messageDTOS = new ArrayList<>();
        messages.forEach(message -> messageDTOS.add(messageDTOConverterService.apply(message)));

        boolean isRead;
        if(member.getId()==conversation.getMember1Id()){
            if(read) {
                conversation.setMember1Read(true);
                conversationRepository.save(conversation);//Save the changes.
            }
            isRead = conversation.isMember1Read();
        }
        else
        {
            if(read){
                conversation.setMember2Read(true);
                conversationRepository.save(conversation);
            }
            isRead = conversation.isMember2Read();
        }


        return conversationDTOConverterService.apply(conversation, messageDTOS, otherMember.getUsername(), isRead);
    }

    public MessageDTO addMessage(MessageRequest messageRequest, String memberUsername){
        Member otherMember = memberRepository.findByUsername(messageRequest.getTargetUsername());
        Member member = memberRepository.findByUsername(memberUsername);

        if(otherMember == null || member.getUsername().equals(otherMember.getUsername())){
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
        message.setSenderUsername(member.getUsername());
        message.setReceiverUsername(otherMember.getUsername());

        Notification notification = new Notification();
        notification.setMemberId(otherMember.getId());
        notification.setNotificationType(NotificationType.NEW_MESSAGE);
        notification.setText("You have a new message from "+ member.getUsername());
        notificationService.save(notification);

        LocalDateTime localDateTime = LocalDateTime.now();
        message.setMessageTime(Timestamp.valueOf(localDateTime));
        messageRepository.saveAndFlush(message);

        if(member.getId()==conversation.getMember1Id()){
            conversation.setMember1Read(true);
            conversation.setMember2Read(false);
        }
        else{
            conversation.setMember1Read(false);
            conversation.setMember2Read(true);
        }
        conversationRepository.save(conversation);

        return messageDTOConverterService.apply(message);
    }

    public List<ConversationDTO> getAllConversations(String username){
        Member member = memberRepository.findByUsername(username);
        List<Conversation> conversations = conversationRepository.getAllByMemberId(member.getId());
        List<ConversationDTO> conversationDTOS = new ArrayList<>();
        conversations.forEach(conversation -> {
            conversationDTOS.add(getById(conversation.getId(), username, false));
        });
        return conversationDTOS;
    }

}
