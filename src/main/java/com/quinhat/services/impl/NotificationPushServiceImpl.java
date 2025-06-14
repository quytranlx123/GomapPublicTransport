/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.quinhat.dto.NotificationDTO;
import com.quinhat.services.NotificationPushService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASUS
 */
@Service
public class NotificationPushServiceImpl implements NotificationPushService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotificationToUser(Integer userId, NotificationDTO notificationDTO) {
        // Gửi thông báo đến client đang lắng nghe /topic/user/{userId}
        messagingTemplate.convertAndSend("/topic/notifications/" + userId, notificationDTO);
    }

}
