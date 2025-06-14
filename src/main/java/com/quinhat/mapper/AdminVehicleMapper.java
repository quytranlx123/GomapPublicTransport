/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author tranngocqui
 */
public class AdminVehicleMapper {

    // Entity -> DTO
    public static AdminVehicleDTO toDTO(Vehicle v) {
        if (v == null) {
            return null;
        }

        return new AdminVehicleDTO(
                v.getId(),
                v.getLicensePlate(),
                v.getVehicleType(),
                v.getDriver(),
                v.getCapacity(),
                v.getLatitude(),
                v.getLongitude(),
                v.getStatus(),
                v.getUpdatedAt(),
                v.getCreatedAt(),
                v.getRouteId() != null ? v.getRouteId().getId() : null,
                v.getRouteId() != null ? v.getRouteId().getName() : null,
                v.getIsActive()
        );
    }

    // DTO -> Entity
    public static Vehicle toEntity(AdminVehicleDTO dto, Route route) {
        if (dto == null) {
            return null;
        }

        Vehicle v = new Vehicle();
        v.setId(dto.getId());
        v.setLicensePlate(dto.getLicensePlate());
        v.setVehicleType(dto.getVehicleType());
        v.setDriver(dto.getDriver());
        v.setCapacity(dto.getCapacity());
        v.setLatitude(dto.getLatitude());
        v.setLongitude(dto.getLongitude());
        v.setStatus(dto.getStatus());
        v.setUpdatedAt(dto.getUpdatedAt());
        v.setCreatedAt(dto.getCreatedAt());
        v.setIsActive(dto.isActive());
        v.setRouteId(route);
        return v;
    }

    // List<Entity> -> List<DTO>
    public static List<AdminVehicleDTO> fromEntityList(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(AdminVehicleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<Vehicle> toEntityList(List<AdminVehicleDTO> dtos, Route route) {
        return dtos.stream()
                .map(dto -> toEntity(dto, route))
                .collect(Collectors.toList());
    }
}
