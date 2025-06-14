/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

/**
 *
 * @author tranngocqui
 */
import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.User;

public class AdminFavoriteRouteMapper {

    public static AdminFavoriteRouteDTO toDTO(FavoriteRoute entity) {
        if (entity == null) {
            return null;
        }

        AdminFavoriteRouteDTO dto = new AdminFavoriteRouteDTO();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRouteId(entity.getRouteId() != null ? entity.getRouteId().getId() : null);
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().getId() : null);

        return dto;
    }

    public static FavoriteRoute toEntity(AdminFavoriteRouteDTO dto) {
        if (dto == null) {
            return null;
        }

        FavoriteRoute entity = new FavoriteRoute();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());

        if (dto.getRouteId() != null) {
            Route route = new Route();
            route.setId(dto.getRouteId());
            entity.setRouteId(route);
        }

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            entity.setUserId(user);
        }

        return entity;
    }

}
