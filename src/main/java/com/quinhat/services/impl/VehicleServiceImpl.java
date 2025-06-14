/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.mapper.AdminVehicleMapper;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.RouteRepository;
import com.quinhat.repositories.UserRepository;
import com.quinhat.repositories.VehicleRepository;
import com.quinhat.services.VehicleService;
import com.quinhat.utils.Common;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tranngocqui
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;
    @Autowired
    private RouteRepository routeRepo;

    @Override
    public List<AdminVehicleDTO> getAllVehicles() {
        return vehicleRepo.getAllVehicles();
    }

    @Override
    public List<Vehicle> getVehiclesByRouteId(int routeId, int page, int pageSize) {
        return this.vehicleRepo.getVehiclesByRouteId(routeId, page, pageSize);
    }

    @Override
    public long countVehiclesByRouteid(int routeId) {
        return this.vehicleRepo.countVehiclesByRouteid(routeId);
    }

    @Override
    public void save(AdminVehicleDTO dto) {
        vehicleRepo.save(dto);
    }

    @Override
    public long countByIsActive(boolean isActive) {
        return this.vehicleRepo.countByIsActive(isActive);
    }

    @Override
    public void delete(List<Integer> ids) {
        vehicleRepo.delete(ids);
    }

    @Override
    public List<AdminVehicleDTO> getVehiclesPaginated(int page, int size) {
        return vehicleRepo.getVehiclesPaginated(page, size);
    }

    @Override
    public long countVehicles() {
        return vehicleRepo.countVehicles();
    }

    @Override
    public Vehicle findById(int id) {
        return vehicleRepo.findById(id);
    }

    @Override
    public AdminVehicleDTO update(AdminVehicleDTO dto) {
        Vehicle existing = vehicleRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        // Chỉ initialize nếu routeId không null
        if (existing.getRouteId() != null) {
            Hibernate.initialize(existing.getRouteId());
        }

        if (dto.getLicensePlate() != null) {
            existing.setLicensePlate(dto.getLicensePlate());
        }

        if (dto.getVehicleType() != null) {
            existing.setVehicleType(dto.getVehicleType());
        }

        if (dto.getDriver() != null) {
            existing.setDriver(dto.getDriver());
        }

        if (dto.getCapacity() > 0) {
            existing.setCapacity(dto.getCapacity());
        }

        if (dto.getLatitude() != 0) {
            existing.setLatitude(dto.getLatitude());
        }

        if (dto.getLongitude() != 0) {
            existing.setLongitude(dto.getLongitude());
        }

        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        if (dto.getRouteId() != null) {
            Route route = routeRepo.findById(dto.getRouteId());
            if (route == null) {
                throw new IllegalArgumentException("Route not found");
            }
            existing.setRouteId(route);
        }

        existing.setIsActive(dto.isActive());

        vehicleRepo.update(existing);
        return AdminVehicleMapper.toDTO(existing);
    }

}
