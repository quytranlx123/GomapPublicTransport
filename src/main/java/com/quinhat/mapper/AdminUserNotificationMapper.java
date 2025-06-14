/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.User;
import com.quinhat.pojo.UserNotification;

/**
 *
 * @author tranngocqui
 */
public class AdminUserNotificationMapper {

    public static AdminUserNotificationDTO toDTO(UserNotification entity) {
        if (entity == null) {
            return null;
        }

        AdminUserNotificationDTO dto = new AdminUserNotificationDTO();
        dto.setId(entity.getId());
        dto.setSendAt(entity.getSendAt());
        dto.setRead(entity.getIsRead());
        dto.setNotificationId(entity.getNotificationId() != null ? entity.getNotificationId().getId() : null);
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().getId() : null);

        return dto;
    }

    public static UserNotification toEntity(AdminUserNotificationDTO dto, Notification notification, User user) {
        if (dto == null) {
            return null;
        }

        UserNotification entity = new UserNotification();
        entity.setId(dto.getId());
        entity.setSendAt(dto.getSendAt());
        entity.setIsRead(dto.isRead());
        entity.setNotificationId(notification);
        entity.setUserId(user);

        return entity;
    }
}
