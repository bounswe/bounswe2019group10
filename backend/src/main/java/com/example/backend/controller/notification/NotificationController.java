package com.example.backend.controller.notification;

import com.example.backend.model.notification.Notification;
import com.example.backend.service.member.JwtUserDetailsService;
import com.example.backend.service.notification.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/read")
    @ApiOperation(value = "Get read notifications.")
    public ResponseEntity<List<Notification>> getReadNotifications () {
        int memberId = jwtUserDetailsService.getUserId();
        return ResponseEntity.ok(notificationService.findAllReadNotificationsOfMember(memberId));
    }

    @GetMapping("/not/read")
    @ApiOperation(value = "Get new notifications.")
    public ResponseEntity<List<Notification>> getAllNotReadNotifications () {
        int memberId = jwtUserDetailsService.getUserId();
        return ResponseEntity.ok(notificationService.findAllNotReadNotifications(memberId));
    }
}
