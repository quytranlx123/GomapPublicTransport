/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public class AdminDashboardForm {

    private List<AdminFavoriteRouteDTO> adminFavoriteRouteDTOs = new ArrayList<>();
    private List<AdminNotificationDTO> adminNotificationDTOs = new ArrayList<>();
    private List<AdminRouteDTO> adminRouteDTOs = new ArrayList<>();
    private List<AdminRouteStationDTO> adminRouteStationDTOs = new ArrayList<>();
    private List<AdminScheduleDTO> adminScheduleDTOs = new ArrayList<>();
    private List<AdminStationDTO> adminStationDTOs = new ArrayList<>();
    private List<AdminTrafficReportDTO> adminTrafficReportDTOs = new ArrayList<>();
    private List<AdminUserDTO> adminUserDTOs = new ArrayList<>();
    private List<AdminUserNotificationDTO> adminUserNotificationDTOs = new ArrayList<>();
    private List<AdminVehicleDTO> adminVehicleDTOs = new ArrayList<>();

    public AdminDashboardForm() {
    }

    public List<AdminFavoriteRouteDTO> getAdminFavoriteRouteDTOs() {
        return adminFavoriteRouteDTOs;
    }

    public void setAdminFavoriteRouteDTOs(List<AdminFavoriteRouteDTO> adminFavoriteRouteDTOs) {
        this.adminFavoriteRouteDTOs = adminFavoriteRouteDTOs;
    }

    public List<AdminNotificationDTO> getAdminNotificationDTOs() {
        return adminNotificationDTOs;
    }

    public void setAdminNotificationDTOs(List<AdminNotificationDTO> adminNotificationDTOs) {
        this.adminNotificationDTOs = adminNotificationDTOs;
    }

    public List<AdminRouteDTO> getAdminRouteDTOs() {
        return adminRouteDTOs;
    }

    public void setAdminRouteDTOs(List<AdminRouteDTO> adminRouteDTOs) {
        this.adminRouteDTOs = adminRouteDTOs;
    }

    public List<AdminRouteStationDTO> getAdminRouteStationDTOs() {
        return adminRouteStationDTOs;
    }

    public void setAdminRouteStationDTOs(List<AdminRouteStationDTO> adminRouteStationDTOs) {
        this.adminRouteStationDTOs = adminRouteStationDTOs;
    }

    public List<AdminScheduleDTO> getAdminScheduleDTOs() {
        return adminScheduleDTOs;
    }

    public void setAdminScheduleDTOs(List<AdminScheduleDTO> adminScheduleDTOs) {
        this.adminScheduleDTOs = adminScheduleDTOs;
    }

    public List<AdminStationDTO> getAdminStationDTOs() {
        return adminStationDTOs;
    }

    public void setAdminStationDTOs(List<AdminStationDTO> adminStationDTOs) {
        this.adminStationDTOs = adminStationDTOs;
    }

    public List<AdminTrafficReportDTO> getAdminTrafficReportDTOs() {
        return adminTrafficReportDTOs;
    }

    public void setAdminTrafficReportDTOs(List<AdminTrafficReportDTO> adminTrafficReportDTOs) {
        this.adminTrafficReportDTOs = adminTrafficReportDTOs;
    }

    public List<AdminUserDTO> getAdminUserDTOs() {
        return adminUserDTOs;
    }

    public void setAdminUserDTOs(List<AdminUserDTO> adminUserDTOs) {
        this.adminUserDTOs = adminUserDTOs;
    }

    public List<AdminUserNotificationDTO> getAdminUserNotificationDTOs() {
        return adminUserNotificationDTOs;
    }

    public void setAdminUserNotificationDTOs(List<AdminUserNotificationDTO> adminUserNotificationDTOs) {
        this.adminUserNotificationDTOs = adminUserNotificationDTOs;
    }

    public List<AdminVehicleDTO> getAdminVehicleDTOs() {
        return adminVehicleDTOs;
    }

    public void setAdminVehicleDTOs(List<AdminVehicleDTO> adminVehicleDTOs) {
        this.adminVehicleDTOs = adminVehicleDTOs;
    }

}
