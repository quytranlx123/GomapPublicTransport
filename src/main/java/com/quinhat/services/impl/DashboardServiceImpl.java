/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.quinhat.services.DashboardService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quinhat.services.FavoriteRouteService;
import com.quinhat.services.NotificationService;
import com.quinhat.services.RouteService;
import com.quinhat.services.RouteStationService;
import com.quinhat.services.ScheduleService;
import com.quinhat.services.StationService;
import com.quinhat.services.TrafficReportService;
import com.quinhat.services.UserNotificationService;
import com.quinhat.services.UserService;
import com.quinhat.services.VehicleService;
import java.util.HashMap;

/**
 *
 * @author tranngocqui
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private FavoriteRouteService favoriteRouteService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RouteStationService routeStationService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TrafficReportService trafficReportService;
    @Autowired
    private UserNotificationService userNotificationService;

    @Override
    public Map<String, Object> getAllData() {

        Map<String, Object> data = new HashMap<>();
        data.put("users", userService.getAllUsers());
        data.put("vehicles", vehicleService.getAllVehicles());
        data.put("favoriteRoutes", favoriteRouteService.getAllFavoriteRoutes());
        data.put("routes", routeService.getAllRoutes());
        data.put("notifications", notificationService.getAllNotifications());
        data.put("routeStations", routeStationService.getAllRouteStations());
        data.put("schedules", scheduleService.getAllSchedules());
        data.put("stations", stationService.getAllStations());
        data.put("trafficReports", trafficReportService.getAllTrafficReports());
        data.put("userNotifications", userNotificationService.getAllUserNotifications());

        return data;
    }

}
