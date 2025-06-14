/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.pojo.UserNotification;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface UserNotificationService {

    public List<AdminUserNotificationDTO> getAllUserNotifications();

    void save(AdminUserNotificationDTO dto);

    void delete(List<Integer> ids);

    UserNotification findById(int id);

    AdminUserNotificationDTO update(AdminUserNotificationDTO dto);

    List<AdminUserNotificationDTO> getUserNotificationsPaginated(int page, int size);

    long countUserNotifications();

    List<UserNotification> getNotificationsByUserId(Integer userId);

    UserNotification create(UserNotification u);

}
