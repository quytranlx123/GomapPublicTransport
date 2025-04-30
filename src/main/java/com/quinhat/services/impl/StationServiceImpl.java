package com.quinhat.services.impl;

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
    public List<Station> getAllStations() {
        return stationRepo.getAllStations();
    }

    @Override
    public void save(Station station) {
        stationRepo.save(station);
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
    public List<RouteStation> getStationsByRouteId(int routeId) {
        return stationRepo.getStationsByRouteId(routeId);
    }
}
