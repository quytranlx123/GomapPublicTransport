/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.dto.AdminStationDTO;
import com.quinhat.dto.AdminUserDTO;
import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.services.NotificationService;
import com.quinhat.services.RouteService;
import com.quinhat.services.StationService;
import com.quinhat.services.UserService;
import com.quinhat.services.VehicleService;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author tranngocqui
 */
@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService NotificationService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private StationService stationService;
    @Autowired
    private VehicleService vehicleService;

    @ModelAttribute("notificationTypes")
    public TrafficReport.ReportType[] notificationTypes() {
        return TrafficReport.ReportType.values();
    }

    @Cacheable("listUserFullnames")
    @ModelAttribute("listUserFullnames")
    public Map<Integer, String> listUsernames() {
        return userService.getAllUsers().stream()
                .collect(Collectors.toMap(AdminUserDTO::getId, AdminUserDTO::getFullName));
    }

    @ModelAttribute("listNotificationNames")
    public Map<Integer, String> listNotificationNames() {
        return NotificationService.getAllNotifications().stream()
                .collect(Collectors.toMap(AdminNotificationDTO::getId, AdminNotificationDTO::getTitle));
    }

    @ModelAttribute("listRouteNames")
    public Map<Integer, String> listRouteNames() {
        return routeService.getAllRoutes().stream()
                .collect(Collectors.toMap(AdminRouteDTO::getId, AdminRouteDTO::getName));
    }

    @ModelAttribute("listStationNames")
    public Map<Integer, String> listStationNames() {
        return stationService.getAllStations().stream()
                .collect(Collectors.toMap(AdminStationDTO::getId, AdminStationDTO::getName));
    }

    @ModelAttribute("listVehicleNames")
    public Map<Integer, String> listVehicleNames() {
        return vehicleService.getAllVehicles().stream()
                .collect(Collectors.toMap(AdminVehicleDTO::getId, AdminVehicleDTO::getLicensePlate));
    }

}
