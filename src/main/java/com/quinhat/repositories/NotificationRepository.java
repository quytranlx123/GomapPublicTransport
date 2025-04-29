package com.quinhat.repositories;

import com.quinhat.pojo.Notification;
import java.util.List;

public interface NotificationRepository {

    List<Notification> getAllNotifications();

    void save(Notification notification);

}
