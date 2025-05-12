/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.ScheduleDTO;
import com.quinhat.pojo.Schedule;
import com.quinhat.services.ScheduleService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/schedules")
public class ApiScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/by-route")
    public ResponseEntity<ApiResponse<List<ScheduleDTO>>> getSchedulesByRouteId(
            @RequestParam(defaultValue = "1") int routeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        List<Schedule> Schedules = this.scheduleService.getSchedulesByRouteId(routeId, page, pageSize);
        long total = this.scheduleService.countSchedulesByRouteId(routeId);
        Integer totalAsInteger = (int) total;

        List<ScheduleDTO> scheduleDTOs = Schedules.stream()
                .map(ScheduleDTO::fromEntity)
                .collect(Collectors.toList());

        ApiResponse<List<ScheduleDTO>> response = new ApiResponse<>(scheduleDTOs, HttpStatus.OK.value(), "Lấy danh sách lịch trình thành công", page, pageSize, totalAsInteger);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-vehicle")
    public ResponseEntity<ApiResponse<List<ScheduleDTO>>> getSchedulesByVehicleId(
            @RequestParam(defaultValue = "1") int vehicleId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        List<Schedule> schedules = this.scheduleService.getSchedulesByVehicleId(vehicleId, page, pageSize);
        long total = this.scheduleService.countSchedulesByVehicleId(vehicleId);
        Integer totalAsInteger = (int) total;

        // Chỉ định rõ các field cần lấy
        Set<String> fields = Set.of("id", "departureTime", "arrivalTime");

        List<ScheduleDTO> scheduleDTOs = schedules.stream()
                .map(schedule -> new ScheduleDTO(schedule, fields))
                .collect(Collectors.toList());

        ApiResponse<List<ScheduleDTO>> response = new ApiResponse<>(scheduleDTOs, HttpStatus.OK.value(), "Lấy danh sách lịch trình thành công", page, pageSize, totalAsInteger);
        return ResponseEntity.ok(response);
    }
}
