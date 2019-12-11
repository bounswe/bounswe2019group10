package com.example.backend.controller.notification;

import com.example.backend.model.notification.Notification;
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

    @GetMapping("/{memberId}")
    @ApiOperation(value = "Get notifications with member id.")
    public ResponseEntity<List<Notification>> getAllByMemberId (@PathVariable int memberId) {
        return ResponseEntity.ok(notificationService.findAllByMemberId(memberId));
    }
}
