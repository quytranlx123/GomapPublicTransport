/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.mapper;

import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.pojo.Schedule;
import com.quinhat.pojo.Vehicle;

/**
 *
 * @author tranngocqui
 */
public class AdminScheduleMapper {

    public static AdminScheduleDTO toDTO(Schedule entity) {
        if (entity == null) {
            return null;
        }

        AdminScheduleDTO dto = new AdminScheduleDTO();
        dto.setId(entity.getId());
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setArrivalTime(entity.getArrivalTime());

        if (entity.getVehicleId() != null) {
            dto.setVehicleId(entity.getVehicleId().getId());
            dto.setVehicleName(entity.getVehicleId().getLicensePlate());
        }

        return dto;
    }

    public static Schedule toEntity(AdminScheduleDTO dto, Vehicle vehicle) {
        if (dto == null) {
            return null;
        }

        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setDepartureTime(dto.getDepartureTime());
        entity.setArrivalTime(dto.getArrivalTime());
        entity.setVehicleId(vehicle);

        return entity;
    }
}
