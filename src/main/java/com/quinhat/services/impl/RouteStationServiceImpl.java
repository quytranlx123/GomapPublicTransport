package com.quinhat.services.impl;

import com.quinhat.pojo.RouteStation;
import com.quinhat.repositories.RouteStationRepository;
import com.quinhat.services.RouteStationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteStationServiceImpl implements RouteStationService {

    @Autowired
    private RouteStationRepository routeStationRepo;

    @Override
    public List<RouteStation> getAllRouteStations() {
        return routeStationRepo.getAllRouteStations();
    }

    @Override
    public void save(RouteStation routeStation) {
        routeStationRepo.save(routeStation);
    }

    @Override
    public List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId) {
        return routeStationRepo.findStationsWithPossibleTransfer(departureStationId, arrivalStationId);
    }
}
