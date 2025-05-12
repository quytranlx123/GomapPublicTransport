/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.StationDTO;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.services.StationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/stations")
public class ApiStationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getStations(@RequestParam Map<String, String> params) {
        List<Station> stations = this.stationService.getStations(params);

        Set<String> fields = Set.of("id", "name", "address", "latitude", "longitude");

        List<StationDTO> stationDTOs = stations.stream()
                .map(station -> new StationDTO(station, fields)).collect(Collectors.toList());

        ApiResponse<List<StationDTO>> response = new ApiResponse<>(stationDTOs, HttpStatus.OK.value(), "Lấy danh sách các trạm thành công", stationDTOs.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-route")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getStationsByRouteId(
            @RequestParam(defaultValue = "1") int routeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize
    ) {
        // Lấy danh sách RouteStation theo routeId
        List<RouteStation> routeStations = this.stationService.getStationsByRouteId(routeId, page, pageSize);
        long total = this.stationService.countStationsByRouteId(routeId);
        Integer totalAsInteger = (int) total;

        // Dùng hàm fromEntity để tạo DTO từ RouteStation
        List<StationDTO> stationDTOs = routeStations.stream()
                .map(StationDTO::fromEntity)
                .collect(Collectors.toList());

        // Trả về trong ApiResponse
        ApiResponse<List<StationDTO>> response = new ApiResponse<>(stationDTOs, HttpStatus.OK.value(), "Lấy danh sách các trạm dừng của tuyến xe thành công", page, pageSize, totalAsInteger);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nearest")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getNearestStations(
            @RequestParam(name = "latitude") float latitude,
            @RequestParam(name = "longitude") float longitude,
            @RequestParam(name = "limit", defaultValue = "1") int limit
    ) {
        List<Station> stations = this.stationService.findNearestStations(latitude, longitude, limit);

        Set<String> fields = Set.of("id", "name", "address", "latitude", "longitude");

        List<StationDTO> stationDTOs = stations.stream()
                .map(station -> new StationDTO(station, fields)).collect(Collectors.toList());

        ApiResponse<List<StationDTO>> response = new ApiResponse<>(stationDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nearest-multiple")
    public ResponseEntity<ApiResponse<Map<String, List<StationDTO>>>> getNearestStationsMultiple(
            @RequestParam float latitude1,
            @RequestParam float longitude1,
            @RequestParam float latitude2,
            @RequestParam float longitude2,
            @RequestParam(required = false, defaultValue = "1") int limit
    ) {
        List<Station> stations1 = this.stationService.findNearestStations(latitude1, longitude1, limit);
        List<Station> stations2 = this.stationService.findNearestStations(latitude2, longitude2, limit);

        Set<String> fields = Set.of("id", "name", "address", "latitude", "longitude");

        List<StationDTO> stationDTOs1 = stations1.stream()
                .map(station -> new StationDTO(station, fields)).collect(Collectors.toList());

        List<StationDTO> stationDTOs2 = stations2.stream()
                .map(station -> new StationDTO(station, fields)).collect(Collectors.toList());

        Map<String, List<StationDTO>> result = new HashMap<>();
        result.put("departureStation", stationDTOs1);
        result.put("arrivalStation", stationDTOs2);

        ApiResponse<Map<String, List<StationDTO>>> response = new ApiResponse<>(result, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
