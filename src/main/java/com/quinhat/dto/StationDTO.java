/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class StationDTO {

    private Integer id;
    private String name;
    private String address;
    private float latitude;
    private float longitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer orderStation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float distance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer duration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer routeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String routeName;

    public StationDTO() {
    }

    public StationDTO(Station station, Set<String> fields) {
        if (fields.contains("id")) {
            this.id = station.getId();
        }
        if (fields.contains("name")) {
            this.name = station.getName();
        }
        if (fields.contains("address")) {
            this.address = station.getAddress();
        }
        if (fields.contains("latitude")) {
            this.latitude = station.getLatitude();
        }
        if (fields.contains("longitude")) {
            this.longitude = station.getLongitude();
        }

    }

    public StationDTO(Integer id, String name, String address, float latitude, float longitude, Integer orderStation, Float distance, Integer duration, Integer routeId, String routeName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orderStation = orderStation;
        this.distance = distance;
        this.duration = duration;
        this.routeId = routeId;
        this.routeName = routeName;
    }

    public static StationDTO fromEntity(RouteStation routeStation) {
        return new StationDTO(
                routeStation.getStationId().getId(),
                routeStation.getStationId().getName(),
                routeStation.getStationId().getAddress(),
                routeStation.getStationId().getLatitude(),
                routeStation.getStationId().getLongitude(),
                routeStation.getOrderStation(),
                routeStation.getDistance(),
                routeStation.getDuration(),
                routeStation.getRouteId().getId(),
                routeStation.getRouteId().getName()
        );
    }

    // Getter Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    /**
     * @return the orderStation
     */
    public Integer getOrderStation() {
        return orderStation;
    }

    /**
     * @param orderStation the orderStation to set
     */
    public void setOrderStation(Integer orderStation) {
        this.orderStation = orderStation;
    }

    /**
     * @return the distance
     */
    public Float getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(Float distance) {
        this.distance = distance;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the routeId
     */
    public Integer getRouteId() {
        return routeId;
    }

    /**
     * @param routeId the routeId to set
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    /**
     * @return the routeName
     */
    public String getRouteName() {
        return routeName;
    }

    /**
     * @param routeName the routeName to set
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
