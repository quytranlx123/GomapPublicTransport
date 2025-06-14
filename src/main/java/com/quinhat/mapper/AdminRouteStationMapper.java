/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;

/**
 *
 * @author tranngocqui
 */
public class AdminRouteStationMapper {

    public static AdminRouteStationDTO toDTO(RouteStation entity) {
        if (entity == null) {
            return null;
        }

        AdminRouteStationDTO dto = new AdminRouteStationDTO();
        dto.setId(entity.getId());
        dto.setOrderStation(entity.getOrderStation());
        dto.setDistance(entity.getDistance());
        dto.setDuration(entity.getDuration());

        if (entity.getRouteId() != null) {
            dto.setRouteId(entity.getRouteId().getId());
        }

        if (entity.getStationId() != null) {
            dto.setStationId(entity.getStationId().getId());
        }

        return dto;
    }

    public static RouteStation toEntity(AdminRouteStationDTO dto, Route route, Station station) {
        if (dto == null) {
            return null;
        }

        RouteStation entity = new RouteStation();
        entity.setId(dto.getId());
        entity.setOrderStation(dto.getOrderStation());
        entity.setDistance(dto.getDistance());
        entity.setDuration(dto.getDuration());
        entity.setRouteId(route);
        entity.setStationId(station);

        return entity;
    }
}
