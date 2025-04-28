package com.quinhat.repositories;

import com.quinhat.pojo.UserNotification;
import java.util.List;

public interface UserNotificationRepository {
    List<UserNotification> getAllUserNotifications();
}
