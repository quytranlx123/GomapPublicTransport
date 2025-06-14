package com.quinhat.dto;

import java.util.Date;

public class AdminVehicleDTO {

    private Integer id;
    private String licensePlate;
    private String vehicleType;
    private String driver;
    private int capacity;
    private float latitude;
    private float longitude;
    private String status;
    private Date updatedAt;
    private Date createdAt;
    private Integer routeId;
    private String routeName;
    private boolean isActive;

    public AdminVehicleDTO() {
    }

    public AdminVehicleDTO(Integer id, String licensePlate, String vehicleType, String driver, int capacity,
            float latitude, float longitude, String status, Date updatedAt, Date createdAt,
            Integer routeId, String routeName, boolean isActive) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.driver = driver;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.routeId = routeId;
        this.routeName = routeName;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
