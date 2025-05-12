/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.User;
import com.quinhat.pojo.UserNotification;
import com.quinhat.services.NotificationService;
import com.quinhat.services.UserNotificationService;
import com.quinhat.services.UserService;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/user-notifications")
public class ApiUserNotificationController {

    @Autowired
    private UserService userDetailsService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserNotificationService userNotificationService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<UserNotification>> createUserNotification(
            @RequestBody Map<String, Integer> body,
            Principal principal) {

        // Lấy user đang đăng nhập
        String username = principal.getName();
        User user = userDetailsService.getUserByUsername(username);

        // Lấy Notification từ ID
        Integer notificationId = body.get("notificationId");
        Notification notification = notificationService.getNotificationById(notificationId);
        if (notification == null) {
            return new ResponseEntity<>(new ApiResponse<>(null, HttpStatus.NOT_FOUND.value(), "Thông báo không tồn tại"), HttpStatus.NOT_FOUND);
        }

        // Tạo UserNotification mới
        UserNotification userNotification = new UserNotification();
        userNotification.setUserId(user);
        userNotification.setNotificationId(notification);
        userNotification.setSendAt(new Date());
        userNotification.setIsRead(false);

        // Gọi service để lưu và đẩy lên Firebase
        UserNotification savedNotification = userNotificationService.create(userNotification);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
