/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quinhat.pojo.Schedule;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class ScheduleDTO {

    private Integer id;
    private Date departureTime;
    private Date arrivalTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vehicleLicensePlate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String vehicleType;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int vehicleCapacity;

    public ScheduleDTO(Integer id, Date departureTime, Date arrivalTime, String vehicleLicensePlate, String vehicleType, int vehicleCapacity) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.vehicleType = vehicleType;
        this.vehicleCapacity = vehicleCapacity;
    }

    public static ScheduleDTO fromEntity(Schedule schedule) {
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getVehicleId().getLicensePlate(),
                schedule.getVehicleId().getVehicleType(),
                schedule.getVehicleId().getCapacity()
        );
    }

    public ScheduleDTO(Schedule schedule, Set<String> fields) {
        if (fields.contains("id")) {
            this.id = schedule.getId();
        }
        if (fields.contains("departureTime")) {
            this.departureTime = schedule.getDepartureTime();
        }
        if (fields.contains("arrivalTime")) {
            this.arrivalTime = schedule.getArrivalTime();
        }
        if (fields.contains("vehicleLicensePlate") && schedule.getVehicleId() != null) {
            this.vehicleLicensePlate = schedule.getVehicleId().getLicensePlate();
        }
        if (fields.contains("vehicleType") && schedule.getVehicleId() != null) {
            this.vehicleType = schedule.getVehicleId().getVehicleType();
        }
        if (fields.contains("vehicleCapacity") && schedule.getVehicleId() != null) {
            this.vehicleCapacity = schedule.getVehicleId().getCapacity();
        }
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the departureTime
     */
    public Date getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the arrivalTime
     */
    public Date getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the vehicleLicensePlate
     */
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    /**
     * @param vehicleLicensePlate the vehicleLicensePlate to set
     */
    public void setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
    }

    /**
     * @return the vehicleType
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return the vehicleCapacity
     */
    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    /**
     * @param vehicleCapacity the vehicleCapacity to set
     */
    public void setVehicleCapacity(int vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
    }

}
