/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.VehicleDTO;
import com.quinhat.pojo.Vehicle;
import com.quinhat.services.VehicleService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/vehicles")
public class ApiVehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/by-route/{routeId}")
    public ResponseEntity<ApiResponse<List<VehicleDTO>>> getStationsByRouteId(
            @PathVariable int routeId
    ) {
        // Lấy danh sách Vehicle theo routeId
        List<Vehicle> vehicles = this.vehicleService.getVehiclesByRouteId(routeId);

        // Dùng hàm fromEntity để tạo DTO từ Vehicle
        List<VehicleDTO> vehicleDTOs = vehicles.stream().map(v -> new VehicleDTO(
                v.getId(),
                v.getLicensePlate(),
                v.getVehicleType(),
                v.getDriver(),
                v.getCapacity(),
                v.getLatitude(),
                v.getLongitude(),
                v.getStatus()
        )).collect(Collectors.toList());

        // Trả về trong ApiResponse
        ApiResponse<List<VehicleDTO>> response = new ApiResponse<>(vehicleDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
