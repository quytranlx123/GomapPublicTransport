/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.pojo.Notification;

/**
 *
 * @author tranngocqui
 */
public class AdminNotificationMapper {

    public static AdminNotificationDTO toDTO(Notification entity) {
        if (entity == null) {
            return null;
        }

        AdminNotificationDTO dto = new AdminNotificationDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setMessageType(entity.getMessageType());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public static Notification toEntity(AdminNotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        Notification entity = new Notification();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setMessage(dto.getMessage());
        entity.setMessageType(dto.getMessageType());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
}
