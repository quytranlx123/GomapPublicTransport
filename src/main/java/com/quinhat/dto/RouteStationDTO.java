package com.quinhat.dto;

import com.quinhat.pojo.RouteStation;

/**
 *
 * @author ASUS
 */
public class RouteStationDTO {

    private StationDTO station;
    private int orderStation;
    private float distance;
    private int duration;

    public RouteStationDTO() {
    }

    public RouteStationDTO(StationDTO station) {
        this.station = station;
    }

    public static RouteStationDTO fromEntity(RouteStation rs) {
        return new RouteStationDTO(StationDTO.fromEntity(rs)); // Chỉ cần chuyển từ RouteStation thành StationDTO
    }

    // Getter Setter
    public StationDTO getStation() {
        return station;
    }

    public void setStation(StationDTO station) {
        this.station = station;
    }

    public int getOrderStation() {
        return orderStation;
    }

    public void setOrderStation(int orderStation) {
        this.orderStation = orderStation;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
