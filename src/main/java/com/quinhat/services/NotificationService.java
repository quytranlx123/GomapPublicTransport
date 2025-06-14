/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.pojo.Notification;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface NotificationService {

    public List<AdminNotificationDTO> getAllNotifications();

    void save(AdminNotificationDTO dto);

    void delete(List<Integer> ids);

    List<AdminNotificationDTO> getNotificationsPaginated(int page, int size);

    long countNotifications();

    Notification findById(int id);

    AdminNotificationDTO update(AdminNotificationDTO dto);

    Notification getNotificationById(Integer id);
}
