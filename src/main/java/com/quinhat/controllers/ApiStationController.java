/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.RouteDTO;
import com.quinhat.dto.StationDTO;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.services.RouteService;
import com.quinhat.services.StationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private RouteService routeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getStations(@RequestParam Map<String, String> params) {
        List<Station> stations = this.stationService.getStations(params);

        List<StationDTO> stationDTOs = stations.stream()
                .map(StationDTO::fromStation)
                .collect(Collectors.toList());

        ApiResponse<List<StationDTO>> response = new ApiResponse<>(stationDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-route/{routeId}")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getStationsByRouteId(
            @PathVariable int routeId
    ) {
        // Lấy danh sách RouteStation theo routeId
        List<RouteStation> routeStations = this.stationService.getStationsByRouteId(routeId);

        // Dùng hàm fromEntity để tạo DTO từ RouteStation
        List<StationDTO> stationDTOs = routeStations.stream()
                .map(StationDTO::fromEntity)
                .collect(Collectors.toList());

        // Trả về trong ApiResponse
        ApiResponse<List<StationDTO>> response = new ApiResponse<>(stationDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nearest")
    public ResponseEntity<ApiResponse<List<StationDTO>>> getNearestStations(
            @RequestParam(name = "latitude") float latitude,
            @RequestParam(name = "longitude") float longitude,
            @RequestParam(name = "limit", defaultValue = "1") int limit
    ) {
        List<Station> stations = this.stationService.findNearestStations(latitude, longitude, limit);

        List<StationDTO> stationDTOs = stations.stream()
                .map(s -> new StationDTO(
                s.getId(),
                s.getName(),
                s.getAddress(),
                s.getLatitude(),
                s.getLongitude(),
                null, // orderStation - chưa có nên để null
                null, // distance - để null nếu Station entity chưa tính
                null, // duration - để null nếu Station entity chưa tính
                null,
                null
        ))
                .collect(Collectors.toList());

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

        List<StationDTO> stationDTOs1 = stations1.stream()
                .map(s -> new StationDTO(
                s.getId(),
                s.getName(),
                s.getAddress(),
                s.getLatitude(),
                s.getLongitude(),
                null, // orderStation
                null, // distance
                null, // duration
                null,
                null
        ))
                .collect(Collectors.toList());

        List<StationDTO> stationDTOs2 = stations2.stream()
                .map(s -> new StationDTO(
                s.getId(),
                s.getName(),
                s.getAddress(),
                s.getLatitude(),
                s.getLongitude(),
                null, // orderStation
                null, // distance
                null, // duration
                null,
                null
        ))
                .collect(Collectors.toList());

        Map<String, List<StationDTO>> result = new HashMap<>();
        result.put("departureStation", stationDTOs1);
        result.put("arrivalStation", stationDTOs2);

        ApiResponse<Map<String, List<StationDTO>>> response = new ApiResponse<>(result, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-routes")
    public ResponseEntity<ApiResponse<List<RouteDTO>>> findRoutesBetweenTwoStations(
            @RequestParam float latitude1,
            @RequestParam float longitude1,
            @RequestParam float latitude2,
            @RequestParam float longitude2,
            @RequestParam(required = false, defaultValue = "1") int limit
    ) {
        // Gọi API nearest-multiple để lấy các trạm gần nhất từ 2 tọa độ
        ResponseEntity<ApiResponse<Map<String, List<StationDTO>>>> nearestStationsResponse
                = getNearestStationsMultiple(latitude1, longitude1, latitude2, longitude2, limit);

        Map<String, List<StationDTO>> stationsMap = nearestStationsResponse.getBody().getData();
        List<StationDTO> departureStations = stationsMap.get("departureStation");
        List<StationDTO> arrivalStations = stationsMap.get("arrivalStation");

        int departureStationId = departureStations.get(0).getId();
        int arrivalStationId = arrivalStations.get(0).getId();

        // Lấy danh sách Route
        List<Route> routes = routeService.findRoutesByStations(departureStationId, arrivalStationId);

        // Convert sang RouteDTO để tránh lazy-loading lỗi
        List<RouteDTO> routeDTOs = routes.stream()
                .map(RouteDTO::fromEntity)
                .collect(Collectors.toList());

        ApiResponse<List<RouteDTO>> response = new ApiResponse<>(routeDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}
