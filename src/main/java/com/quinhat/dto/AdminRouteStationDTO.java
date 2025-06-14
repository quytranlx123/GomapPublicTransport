package com.quinhat.dto;

public class AdminRouteStationDTO {

    private Integer id;
    private Integer orderStation; // dùng Integer thay vì int
    private Float distance;       // dùng Float thay vì float
    private Integer duration;
    private Integer routeId;
    private Integer stationId;

    public AdminRouteStationDTO() {
    }

    public AdminRouteStationDTO(Integer id, Integer orderStation, Float distance, Integer duration, Integer routeId, Integer stationId) {
        this.id = id;
        this.orderStation = orderStation;
        this.distance = distance;
        this.duration = duration;
        this.routeId = routeId;
        this.stationId = stationId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderStation() {
        return orderStation;
    }

    public void setOrderStation(Integer orderStation) {
        this.orderStation = orderStation;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
