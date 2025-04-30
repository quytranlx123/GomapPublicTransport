/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tranngocqui
 */
public interface RouteService {

    //Qui
    public List<Route> getAllRoutes();

    void save(Route route);

    Route getRouteById_Qui(int id);
    //Qui

    // Tìm các tuyến xe từ điểm đi đến điểm đến
    List<Route> getRoutesByStartAndEndPoints(Map<String, String> params);

    // Tạo tuyến xe mới
    Route createRoute(Map<String, String> params);

    // Cập nhật thông tin tuyến xe
    Route updateRoute(int id, Map<String, String> params);

    // Lấy thông tin tuyến xe theo ID
    Route getRouteById(int id);

    // Xóa tuyến xe
    void deleteRoute(int id);

    // Tìm trạm theo tọa độ
    List<Station> getStationsByCoordinates(Map<String, String> params);

    // Lấy danh sách các trạm trong tuyến xe
    List<RouteStation> getRouteStations(int routeId);

    // Lấy danh sách các tuyến xe
    List<Route> getRoutes(Map<String, String> params);

    List<Route> findRoutesByStations(int departureStationId, int arrivalStationId);

}
