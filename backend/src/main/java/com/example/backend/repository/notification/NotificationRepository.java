package com.example.backend.repository.notification;

import com.example.backend.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

    List<Notification> getAllByMemberId(int memberId);

    List<Notification> getAllByMemberIdAndRead(int memberId, boolean read);
}
