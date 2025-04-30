package com.quinhat.repositories;

import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.List;
import java.util.Map;

public interface RouteRepository {

    //Qui
    List<Route> getAllRoutes();

    void save(Route route);

    Route getRouteById_Qui(int id);
    //Qui
    
    
    // Tìm các tuyến xe từ điểm đi đến điểm đến
    List<Route> getRoutesByStartAndEndPoints(Map<String, String> params);

    // Tạo tuyến xe mới
    Route createRoute(Route route);

    // Cập nhật thông tin tuyến xe
    Route updateRoute(Route route);

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

    List<RouteStation> getAllRouteStations();

    List<Route> getRoutesByIds(List<Integer> routeIds);

    // Tìm tuyến đường
    List<Route> findRoutesByStations(int departureStationId, int arrivalStationId);

}
