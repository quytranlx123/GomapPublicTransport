package com.quinhat.services.impl;

import com.quinhat.pojo.UserNotification;
import com.quinhat.repositories.UserNotificationRepository;
import com.quinhat.services.UserNotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepo;

    @Override
    public List<UserNotification> getAllUserNotifications() {
        return userNotificationRepo.getAllUserNotifications();
    }

    @Override
    public void save(UserNotification userNotification) {
        userNotificationRepo.save(userNotification);
    }
}
