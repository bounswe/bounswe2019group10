package com.example.backend.service.notification;


import com.example.backend.model.notification.Notification;
import com.example.backend.repository.notification.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification save (Notification notification) {
       return notificationRepository.save(notification);
    }

    public List<Notification> findAllByMemberId (int memberId) {
        return notificationRepository.getAllByMemberId(memberId);
    }

    public List<Notification> findAllNotReadNotifications(int memberId) {
        return notificationRepository.getAllByMemberIdAndRead(memberId, false);
    }

    public List<Notification> findAllReadNotificationsOfMember(int memberId) {
        return notificationRepository.getAllByMemberIdAndRead(memberId, true);
    }

    public List<Notification> makeNotificationsRead(List<Notification> notifications) {
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
        return notifications;
    }
}
