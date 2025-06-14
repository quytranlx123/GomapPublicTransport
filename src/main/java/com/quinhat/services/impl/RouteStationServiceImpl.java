package com.quinhat.services.impl;

import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.mapper.AdminRouteStationMapper;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.repositories.RouteRepository;
import com.quinhat.repositories.RouteStationRepository;
import com.quinhat.repositories.StationRepository;
import com.quinhat.services.RouteStationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteStationServiceImpl implements RouteStationService {

    @Autowired
    private RouteStationRepository routeStationRepo;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StationRepository stationRepository;

    @Override
    public List<AdminRouteStationDTO> getAllRouteStations() {
        return routeStationRepo.getAllRouteStations();
    }

    @Override
    public List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId) {
        return routeStationRepo.findStationsWithPossibleTransfer(departureStationId, arrivalStationId);
    }

    @Override
    public void save(AdminRouteStationDTO dto) {
        routeStationRepo.save(dto);
    }

    @Override
    public void delete(List<Integer> ids) {
        routeStationRepo.delete(ids);
    }

    @Override
    public List<AdminRouteStationDTO> getRouteStationsPaginated(int page, int size) {
        return routeStationRepo.getRouteStationsPaginated(page, size);
    }

    @Override
    public long countRouteStations() {
        return routeStationRepo.countRouteStations();
    }

    @Override
    public RouteStation findById(int id) {
        return routeStationRepo.findById(id);
    }

    @Override
    public AdminRouteStationDTO update(AdminRouteStationDTO dto) {
        RouteStation existing = routeStationRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("RouteStation not found");
        }

        if (dto.getDistance() != null) {
            existing.setDistance(dto.getDistance());
        }

        if (dto.getDuration() != null) {
            existing.setDuration(dto.getDuration());
        }

        if (dto.getOrderStation() != null) {
            existing.setOrderStation(dto.getOrderStation());
        }

        if (dto.getDistance() != null) {
            existing.setDistance(dto.getDistance());
        }

        if (dto.getDuration() != null) {
            existing.setDuration(dto.getDuration());
        }

        if (dto.getRouteId() != null) {
            Route r = routeRepository.findById(dto.getRouteId());
            if (r == null) {
                throw new IllegalArgumentException("Route not found");
            }
            existing.setRouteId(r);
        }

        if (dto.getStationId() != null && dto.getStationId() != null) {
            Station s = stationRepository.findById(dto.getStationId());
            if (s == null) {
                throw new IllegalArgumentException("Station not found");
            }
            existing.setStationId(s);
        }

        routeStationRepo.update(existing);
        return AdminRouteStationMapper.toDTO(existing);
    }

}
