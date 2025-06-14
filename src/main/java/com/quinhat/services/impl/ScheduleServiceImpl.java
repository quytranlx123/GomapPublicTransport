package com.quinhat.services.impl;

import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.mapper.AdminScheduleMapper;
import com.quinhat.pojo.Schedule;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.ScheduleRepository;
import com.quinhat.repositories.VehicleRepository;
import com.quinhat.services.ScheduleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepo;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<AdminScheduleDTO> getAllSchedules() {
        return scheduleRepo.getAllSchedules();
    }

    @Override
    public List<Schedule> getSchedulesByRouteId(int routeId, int page, int pageSize) {
        return scheduleRepo.getSchedulesByRouteId(routeId, page, pageSize);
    }

    @Override
    public long countSchedulesByRouteId(int routeId) {
        return scheduleRepo.countSchedulesByRouteId(routeId);
    }

    @Override
    public List<Schedule> getSchedulesByVehicleId(int vehicleId, int page, int pageSize) {
        return scheduleRepo.getSchedulesByVehicleId(vehicleId, page, pageSize);
    }

    @Override
    public long countSchedulesByVehicleId(int vehicleId) {
        return scheduleRepo.countSchedulesByVehicleId(vehicleId);
    }

    @Override
    public void save(AdminScheduleDTO dto) {
        scheduleRepo.save(dto);
    }

    @Override
    public void delete(List<Integer> ids) {
        scheduleRepo.delete(ids);
    }

    @Override
    public List<AdminScheduleDTO> getSchedulesPaginated(int page, int size) {
        return scheduleRepo.getSchedulesPaginated(page, size);
    }

    @Override
    public long countSchedules() {
        return scheduleRepo.countSchedules();
    }

    @Override
    public Schedule findById(int id) {
        return scheduleRepo.findById(id);
    }

    @Override
    public AdminScheduleDTO update(AdminScheduleDTO dto) {
        Schedule existing = scheduleRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Schedule not found");
        }

        if (dto.getDepartureTime() != null) {
            existing.setDepartureTime(dto.getDepartureTime());
        }
        if (dto.getArrivalTime() != null) {
            existing.setArrivalTime(dto.getArrivalTime());
        }

        if (dto.getVehicleId() != null) {
            Vehicle v = vehicleRepository.findById(dto.getVehicleId());
            if (v == null) {
                throw new IllegalArgumentException("Vehicle not found");
            }
            existing.setVehicleId(v);
        }

        scheduleRepo.update(existing);
        return AdminScheduleMapper.toDTO(existing);
    }

}
