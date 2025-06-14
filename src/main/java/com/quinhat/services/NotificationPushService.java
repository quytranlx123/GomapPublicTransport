/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.NotificationDTO;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface NotificationPushService {

    void sendNotificationToUser(Integer userId, NotificationDTO notificationDTO);

}
