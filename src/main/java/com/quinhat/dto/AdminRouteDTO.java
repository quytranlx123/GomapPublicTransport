package com.quinhat.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class AdminRouteDTO {

    private Integer id;
    private String startPoint;
    private String endPoint;
    private String name;
    private String status;
    private String frequency;
    @DateTimeFormat(pattern = "HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Date endTime;
    private float distance;
    private int duration;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    public AdminRouteDTO() {
    }

    public AdminRouteDTO(Integer id, String startPoint, String endPoint, String name, String status,
            String frequency, Date startTime, Date endTime, float distance, int duration, Date createdAt) {
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
    }

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
}
