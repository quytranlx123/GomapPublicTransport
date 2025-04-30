/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories;

import java.util.List;
import com.quinhat.pojo.Vehicle;
import com.quinhat.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tranngocqui
 */
public interface VehicleRepository {

    //Qui
    public List<Vehicle> getAllVehicles();

    void save(Vehicle vehicle);
    //Qui

    List<Vehicle> getVehiclesByRouteId(int routeId);

}
