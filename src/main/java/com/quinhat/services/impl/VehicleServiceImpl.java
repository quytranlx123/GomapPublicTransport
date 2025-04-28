/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.UserRepository;
import com.quinhat.repositories.VehicleRepository;
import com.quinhat.services.VehicleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tranngocqui
 */
@Service
public class VehicleServiceImpl implements VehicleService{
    
    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.getAllVehicles();
    }
    
}
