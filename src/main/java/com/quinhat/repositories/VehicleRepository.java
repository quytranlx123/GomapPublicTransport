/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.dto.AdminUserDTO;
import com.quinhat.dto.AdminVehicleDTO;
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
    public List<AdminVehicleDTO> getAllVehicles();

    void save(AdminVehicleDTO dto);

    void delete(List<Integer> ids);

    List<AdminVehicleDTO> getVehiclesPaginated(int page, int size);

    long countVehicles();

    Vehicle findById(int id);

    void update(Vehicle u);

    //Qui
    List<Vehicle> getVehiclesByRouteId(int routeId, int page, int pageSize);

    long countVehiclesByRouteid(int routeId);

    long countByIsActive(boolean isActive);

}
