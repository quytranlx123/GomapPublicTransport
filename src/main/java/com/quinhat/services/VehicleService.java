/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminUserDTO;
import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.pojo.User;
import java.util.List;
import com.quinhat.pojo.Vehicle;

/**
 *
 * @author tranngocqui
 */
public interface VehicleService {

    //Qui
    List<AdminVehicleDTO> getAllVehicles();

    void save(AdminVehicleDTO dto);

    void delete(List<Integer> ids);

    List<AdminVehicleDTO> getVehiclesPaginated(int page, int size);

    long countVehicles();

    Vehicle findById(int id);

    AdminVehicleDTO update(AdminVehicleDTO dto);

    //Qui
    List<Vehicle> getVehiclesByRouteId(int routeId, int page, int pageSize);

    long countVehiclesByRouteid(int routeId);

    long countByIsActive(boolean isActive);

}
