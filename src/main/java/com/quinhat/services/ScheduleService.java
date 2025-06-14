/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.pojo.Schedule;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface ScheduleService {

    public List<AdminScheduleDTO> getAllSchedules();

    void save(AdminScheduleDTO dto);

    void delete(List<Integer> ids);

    List<AdminScheduleDTO> getSchedulesPaginated(int page, int size);

    long countSchedules();

    Schedule findById(int id);

    AdminScheduleDTO update(AdminScheduleDTO dto);

    List<Schedule> getSchedulesByRouteId(int routeId, int page, int pageSize);

    long countSchedulesByRouteId(int routeId);

    List<Schedule> getSchedulesByVehicleId(int vehicleId, int page, int pageSize);

    long countSchedulesByVehicleId(int vehicleId);

}
