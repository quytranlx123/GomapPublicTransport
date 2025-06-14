package com.quinhat.dto;

import java.util.Date;

public class AdminTrafficReportDTO {

    private Integer id;
    private String title;
    private String address;
    private float latitude;
    private float longitude;
    private String image;
    private String description;
    private Date createdAt;
    private boolean isVerified;
    private Integer userId; // chỉ lấy id của User để tránh vòng lặp JSON
    private String type;    // REPORT hoặc RATING (enum dưới dạng string)

    public AdminTrafficReportDTO() {
    }

    public AdminTrafficReportDTO(Integer id, String title, String address, float latitude, float longitude,
            String image, String description, Date createdAt,
            boolean isVerified, Integer userId, String type) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
        this.isVerified = isVerified;
        this.userId = userId;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
