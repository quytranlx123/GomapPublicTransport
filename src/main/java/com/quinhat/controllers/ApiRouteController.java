/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.RouteDTO;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.services.RouteService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/routes")
public class ApiRouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/createRoute")
    public ResponseEntity<ApiResponse<Route>> createRoute(@RequestBody Map<String, String> params) {
        Route createdRoute = routeService.createRoute(params);
        ApiResponse<Route> response = new ApiResponse<>(createdRoute, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> getRouteById(@PathVariable int id) {
        Route route = routeService.getRouteById(id);
        if (route != null) {
            ApiResponse<Route> response = new ApiResponse<>(route, HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Route> response = new ApiResponse<>(null, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> updateRoute(@PathVariable int id, @RequestBody Map<String, String> params) {
        Route updatedRoute = routeService.updateRoute(id, params);
        if (updatedRoute != null) {
            ApiResponse<Route> response = new ApiResponse<>(updatedRoute, HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<Route> response = new ApiResponse<>(null, HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoute(@PathVariable int id) {
        routeService.deleteRoute(id);
        ApiResponse<Void> response = new ApiResponse<>(null, HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/{id}/stations")
    public ResponseEntity<ApiResponse<List<RouteStation>>> getRouteStations(@PathVariable int id) {
        List<RouteStation> routeStations = routeService.getRouteStations(id);
        ApiResponse<List<RouteStation>> response = new ApiResponse<>(routeStations, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stations")
    public ResponseEntity<ApiResponse<List<Station>>> getStationsByCoordinates(@RequestParam Map<String, String> params) {
        List<Station> stations = routeService.getStationsByCoordinates(params);
        ApiResponse<List<Station>> response = new ApiResponse<>(stations, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-routes")
    public ResponseEntity<ApiResponse<List<RouteDTO>>> getRoutes(@RequestParam Map<String, String> params) {
        // Lấy danh sách Route từ service
        List<Route> routes = this.routeService.getRoutes(params);

        // Chuyển đổi từ Route entity sang RouteDTO
        List<RouteDTO> routeDTOs = routes.stream()
                .map(RouteDTO::fromEntityWithoutStations)
                .collect(Collectors.toList());

        // Trả về ApiResponse chứa danh sách RouteDTO
        ApiResponse<List<RouteDTO>> response = new ApiResponse<>(routeDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

}
