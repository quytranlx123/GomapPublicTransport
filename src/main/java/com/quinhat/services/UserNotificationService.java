/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.UserNotification;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface UserNotificationService {

    public List<UserNotification> getAllUserNotifications();

    void save(UserNotification userNotification);

    List<UserNotification> getNotificationsByUserId(Integer userId);

    UserNotification create(UserNotification u);

}
