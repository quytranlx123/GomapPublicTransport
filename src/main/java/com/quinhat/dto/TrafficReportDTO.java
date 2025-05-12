package com.quinhat.dto;

import com.quinhat.pojo.TrafficReport;
import java.util.Date;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class TrafficReportDTO {

    private Integer id;
    private String title;
    private String address;
    private float latitude;
    private float longitude;
    private String image;
    private String description;
    private Date createdAt;
    private boolean isVerified;
    private UserDTO user;

    // Constructor
    public TrafficReportDTO(Integer id, String title, String address, float latitude, float longitude, String image,
            String description, Date createdAt, boolean isVerified, UserDTO user) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
        this.isVerified = isVerified;
        this.user = user;
    }

    public static TrafficReportDTO fromEntity(TrafficReport trafficReport) {
        return new TrafficReportDTO(
                trafficReport.getId(),
                trafficReport.getTitle(),
                trafficReport.getAddress(),
                trafficReport.getLatitude(),
                trafficReport.getLongitude(),
                trafficReport.getImage(),
                trafficReport.getDescription(),
                trafficReport.getCreatedAt(),
                trafficReport.getIsVerified(),
                null
        );
    }

    // Getters & Setters
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

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
