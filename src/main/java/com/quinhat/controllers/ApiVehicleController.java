/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminVehicleDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AdminVehicleDTO>>> getAllVehicles() {
        // Gọi service để lấy tất cả phương tiện
        List<AdminVehicleDTO> vehicles = vehicleService.getAllVehicles();

        // Gói dữ liệu trả về trong ApiResponse
        ApiResponse<List<AdminVehicleDTO>> response = new ApiResponse<>(
                vehicles,
                HttpStatus.OK.value(),
                "Lấy danh sách tất cả phương tiện thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-route")
    public ResponseEntity<ApiResponse<List<VehicleDTO>>> getStationsByRouteId(
            @RequestParam(defaultValue = "1") int routeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        // Lấy danh sách Vehicle theo routeId
        List<Vehicle> vehicles = this.vehicleService.getVehiclesByRouteId(routeId, page, pageSize);
        long total = this.vehicleService.countVehiclesByRouteid(routeId);
        Integer totalAsInteger = (int) total;

        // Dùng hàm fromEntity để tạo DTO từ Vehicle
        List<VehicleDTO> vehicleDTOs = vehicles.stream().map(VehicleDTO::fromEntity).collect(Collectors.toList());

        // Trả về trong ApiResponse
        ApiResponse<List<VehicleDTO>> response = new ApiResponse<>(vehicleDTOs, HttpStatus.OK.value(), "Lấy danh sách các phương tiện thành công", page, pageSize, totalAsInteger);
        return ResponseEntity.ok(response);
    }
}
