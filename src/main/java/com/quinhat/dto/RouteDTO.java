package com.quinhat.dto;

import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RouteDTO {

    private Integer id;
    private String startPoint;
    private String endPoint;
    private String name;
    private String status;
    private String frequency;
    private Date startTime;
    private Date endTime;
    private float distance;
    private int duration;
    private Date createdAt;
    private List<StationDTO> routeStations;

    public RouteDTO(Integer id, String startPoint, String endPoint, String name, String status, String frequency,
            Date startTime, Date endTime, float distance, int duration, Date createdAt,
            List<StationDTO> routeStations) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.name = name;
        this.status = status;
        this.frequency = frequency;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.duration = duration;
        this.createdAt = createdAt;
        this.routeStations = routeStations;
    }

    public RouteDTO(Integer id, String startPoint, String endPoint, String name, String status, String frequency,
            Date startTime, Date endTime, float distance, int duration, Date createdAt) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.name = name;
        this.status = status;
        this.frequency = frequency;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.duration = duration;
        this.createdAt = createdAt;
        this.routeStations = null;
    }

    public static RouteDTO fromEntity(Route route) {
        return new RouteDTO(
                route.getId(),
                route.getStartPoint(),
                route.getEndPoint(),
                route.getName(),
                route.getStatus(),
                route.getFrequency(),
                route.getStartTime(),
                route.getEndTime(),
                route.getDistance(),
                route.getDuration(),
                route.getCreatedAt(),
                route.getRouteStationSet().stream()
                        .map(routeStation -> StationDTO.fromEntity(routeStation))
                        .collect(Collectors.toList())
        );
    }

    public static RouteDTO fromEntityWithoutStations(Route route) {
        return new RouteDTO(
                route.getId(),
                route.getStartPoint(),
                route.getEndPoint(),
                route.getName(),
                route.getStatus(),
                route.getFrequency(),
                route.getStartTime(),
                route.getEndTime(),
                route.getDistance(),
                route.getDuration(),
                route.getCreatedAt()
        );
    }

    public static RouteDTO fromRouteStations(List<RouteStation> routeStations) {
        if (routeStations == null || routeStations.isEmpty()) {
            return null;
        }

        Route route = routeStations.get(0).getRouteId(); // Giả định tất cả cùng 1 Route

//        List<StationDTO> stationDTOs = routeStations.stream()
//                .sorted((r1, r2) -> Integer.compare(r1.getOrderStation(), r2.getOrderStation()))
//                .map(StationDTO::fromEntity)
//                .collect(Collectors.toList());
        List<StationDTO> stationDTOs = routeStations.stream().map(StationDTO::fromEntity).collect(Collectors.toList());

        return new RouteDTO(
                route.getId(),
                route.getStartPoint(),
                route.getEndPoint(),
                route.getName(),
                route.getStatus(),
                route.getFrequency(),
                route.getStartTime(),
                route.getEndTime(),
                route.getDistance(),
                route.getDuration(),
                route.getCreatedAt(),
                stationDTOs
        );
    }

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<StationDTO> getRouteStations() {
        return routeStations;
    }

    public void setRouteStations(List<StationDTO> routeStations) {
        this.routeStations = routeStations;
    }
}
