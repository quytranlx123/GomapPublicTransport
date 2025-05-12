/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.RouteDTO;
import com.quinhat.dto.StationDTO;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.services.RouteStationService;
import com.quinhat.services.StationService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/route-stations")
public class ApiRouteStationController {

    @Autowired
    private StationService stationService;

    @Autowired
    private RouteStationService routeStationService;

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

    @GetMapping("/find-path")
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

        ApiResponse<Map<String, List<StationDTO>>> nearestBody = nearestStationsResponse.getBody();
        if (nearestBody == null || nearestBody.getData() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Lỗi lấy dữ liệu trạm gần nhất"));
        }

        Map<String, List<StationDTO>> stationsMap = nearestBody.getData();
        List<StationDTO> departureStations = stationsMap.getOrDefault("departureStation", Collections.emptyList());
        List<StationDTO> arrivalStations = stationsMap.getOrDefault("arrivalStation", Collections.emptyList());

        if (departureStations.isEmpty() || arrivalStations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, HttpStatus.BAD_REQUEST.value(), "Không tìm thấy trạm đi hoặc trạm đến"));
        }

        int departureStationId = departureStations.get(0).getId();
        int arrivalStationId = arrivalStations.get(0).getId();

        // Lấy danh sách tuyến đường giữa hai trạm
        List<List<RouteStation>> result = routeStationService.findStationsWithPossibleTransfer(departureStationId, arrivalStationId);

        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, HttpStatus.NOT_FOUND.value(), "Không tìm thấy tuyến đường phù hợp"));
        }

        // Chuyển đổi các tuyến đường thành RouteDTO
        List<RouteDTO> routeDTOs = result.stream()
                .map(RouteDTO::fromRouteStations)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(routeDTOs, HttpStatus.OK.value(), "Tìm tuyến đường thành công", routeDTOs.size()));
    }

}
