package com.quinhat.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class AdminScheduleDTO {

    private Integer id;
    @DateTimeFormat(pattern = "HH:mm")
    private Date departureTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Date arrivalTime;
    private Integer vehicleId;   // ID phương tiện
    private String vehicleName;  // Tùy chọn: tên phương tiện hoặc biển số xe

    public AdminScheduleDTO() {
    }

    public AdminScheduleDTO(Integer id, Date departureTime, Date arrivalTime, Integer vehicleId, String vehicleName) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
}
