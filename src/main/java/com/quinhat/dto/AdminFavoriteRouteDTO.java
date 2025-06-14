package com.quinhat.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class AdminFavoriteRouteDTO {

    private Integer id;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date createdAt;
    private Integer routeId;
    private Integer userId;

    public AdminFavoriteRouteDTO() {
    }

    public AdminFavoriteRouteDTO(Integer id, Date createdAt, Integer routeId, Integer userId) {
        this.id = id;
        this.createdAt = createdAt;
        this.routeId = routeId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
