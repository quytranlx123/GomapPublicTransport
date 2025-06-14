package com.quinhat.services.impl;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.mapper.AdminStationMapper;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.repositories.StationRepository;
import com.quinhat.services.StationService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepo;

    @Override
    public List<AdminStationDTO> getAllStations() {
        return stationRepo.getAllStations();
    }

    @Override
    public List<Station> findNearestStations(float latitude, float longitude, int limit) {
        return this.stationRepo.findNearestStations(latitude, longitude, limit);
    }

    @Override
    public List<Station> getStations(Map<String, String> params) {
        return this.stationRepo.getStations(params);
    }

    @Override
    public List<RouteStation> getStationsByRouteId(int routeId, int page, int pageSize) {
        return stationRepo.getStationsByRouteId(routeId, page, pageSize);
    }

    @Override
    public long countStationsByRouteId(int routeId) {
        return stationRepo.countStationsByRouteId(routeId);
    }

    @Override
    public void save(AdminStationDTO dto) {
        stationRepo.save(dto);
    }

    @Override
    public void delete(List<Integer> ids) {
        stationRepo.delete(ids);
    }

    @Override
    public List<AdminStationDTO> getStationsPaginated(int page, int size) {
        return stationRepo.getStationsPaginated(page, size);
    }

    @Override
    public long countStations() {
        return stationRepo.countStations();
    }

    @Override
    public Station findById(int id) {
        return stationRepo.findById(id);
    }

    @Override
    public AdminStationDTO update(AdminStationDTO dto) {
        Station existing = stationRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Station not found");
        }

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getLatitude() != 0) {
            existing.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != 0) {
            existing.setLongitude(dto.getLongitude());
        }
        if (dto.getAddress() != null) {
            existing.setAddress(dto.getAddress());
        }

        stationRepo.update(existing);
        return AdminStationMapper.toDTO(existing);
    }

}
