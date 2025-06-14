package com.quinhat.repositories;

import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.pojo.UserNotification;
import java.util.List;

public interface UserNotificationRepository {

    List<AdminUserNotificationDTO> getAllUserNotifications();

    void save(UserNotification un);

    void delete(List<Integer> ids);

    UserNotification findById(int id);

    void update(UserNotification u);

    List<AdminUserNotificationDTO> getUserNotificationsPaginated(int page, int size);

    long countUserNotifications();

    List<UserNotification> getNotificationsByUserId(Integer userId);

    UserNotification create(UserNotification u);

}
