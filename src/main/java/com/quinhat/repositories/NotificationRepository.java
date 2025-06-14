package com.quinhat.repositories;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.pojo.Notification;
import java.util.List;

public interface NotificationRepository {

    List<AdminNotificationDTO> getAllNotifications();

    void save(AdminNotificationDTO dto);

    void delete(List<Integer> ids);

    List<AdminNotificationDTO> getNotificationsPaginated(int page, int size);

    long countNotifications();

    void update(Notification n);

    Notification findById(int id);

    Notification getNotificationById(Integer id);

}
