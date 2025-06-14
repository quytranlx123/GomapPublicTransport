/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.pojo.Schedule;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface ScheduleRepository {

    List<AdminScheduleDTO> getAllSchedules();

    void save(AdminScheduleDTO dto);

    void delete(List<Integer> ids);

    List<AdminScheduleDTO> getSchedulesPaginated(int page, int size);

    long countSchedules();

    void update(Schedule sc);

    Schedule findById(int id);

    List<Schedule> getSchedulesByRouteId(int routeId, int page, int pageSize);

    long countSchedulesByRouteId(int routeId);

    List<Schedule> getSchedulesByVehicleId(int vehicleId, int page, int pageSize);

    long countSchedulesByVehicleId(int vehicleId);

}
