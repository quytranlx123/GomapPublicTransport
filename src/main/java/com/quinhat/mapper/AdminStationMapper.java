/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.pojo.Station;

/**
 *
 * @author tranngocqui
 */
public class AdminStationMapper {

    public static AdminStationDTO toDTO(Station entity) {
        if (entity == null) {
            return null;
        }

        AdminStationDTO dto = new AdminStationDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    public static Station toEntity(AdminStationDTO dto) {
        if (dto == null) {
            return null;
        }

        Station entity = new Station();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setAddress(dto.getAddress());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
}
