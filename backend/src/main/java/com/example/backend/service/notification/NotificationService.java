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
        List<Notification> notifications = notificationRepository.getAllByMemberIdAndRead(memberId, false);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
        notifications.forEach(notification -> notification.setRead(false));
        return notifications;
    }

    public List<Notification> findAllReadNotificationsOfMember(int memberId) {
        return notificationRepository.getAllByMemberIdAndRead(memberId, true);
    }
}
