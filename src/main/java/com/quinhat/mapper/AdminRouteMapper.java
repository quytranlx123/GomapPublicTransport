/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.pojo.Route;

/**
 *
 * @author tranngocqui
 */
public class AdminRouteMapper {

    public static AdminRouteDTO toDTO(Route entity) {
        if (entity == null) {
            return null;
        }

        AdminRouteDTO dto = new AdminRouteDTO();
        dto.setId(entity.getId());
        dto.setStartPoint(entity.getStartPoint());
        dto.setEndPoint(entity.getEndPoint());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setFrequency(entity.getFrequency());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setDistance(entity.getDistance());
        dto.setDuration(entity.getDuration());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public static Route toEntity(AdminRouteDTO dto) {
        if (dto == null) {
            return null;
        }

        Route entity = new Route();
        entity.setId(dto.getId());
        entity.setStartPoint(dto.getStartPoint());
        entity.setEndPoint(dto.getEndPoint());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setFrequency(dto.getFrequency());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setDistance(dto.getDistance());
        entity.setDuration(dto.getDuration());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
}
