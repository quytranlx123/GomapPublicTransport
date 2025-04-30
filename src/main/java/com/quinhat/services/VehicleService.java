/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.User;
import java.util.List;
import com.quinhat.pojo.Vehicle;

/**
 *
 * @author tranngocqui
 */
public interface VehicleService {

    //Qui
    List<Vehicle> getAllVehicles();

    void save(Vehicle vehicle);
    //Qui

    List<Vehicle> getVehiclesByRouteId(int routeId);

}
