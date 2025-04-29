package com.quinhat.services.impl;

import com.quinhat.pojo.Notification;
import com.quinhat.repositories.NotificationRepository;
import com.quinhat.services.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepo.getAllNotifications();
    }

    @Override
    public void save(Notification notification) {
        notificationRepo.save(notification);
    }
}
