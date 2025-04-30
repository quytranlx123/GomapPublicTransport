package com.quinhat.services.impl;

import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;

import com.quinhat.repositories.RouteRepository;
import com.quinhat.services.RouteService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.getAllRoutes();
    }

    @Override
    public void save(Route route) {
        routeRepository.save(route);
    }

    @Override
    public Route getRouteById_Qui(int id) {
        return routeRepository.getRouteById(id);
    }
    
    
    @Override
    public List<Route> getRoutesByStartAndEndPoints(Map<String, String> params) {
        return routeRepository.getRoutesByStartAndEndPoints(params);

    }

    @Override
    public Route createRoute(Map<String, String> params) {
        Route route = new Route();
        route.setStartPoint(params.get("startPoint"));
        route.setEndPoint(params.get("endPoint"));
        route.setName(params.get("name"));
        route.setStatus(params.get("status"));
        route.setFrequency(params.get("frequency"));
        route.setDistance(Float.parseFloat(params.get("distance")));
        route.setDuration(Integer.parseInt(params.get("duration")));
        return routeRepository.createRoute(route);
    }

    @Override
    public Route updateRoute(int id, Map<String, String> params) {
        Route route = routeRepository.getRouteById(id);
        if (route != null) {
            route.setStartPoint(params.get("startPoint"));
            route.setEndPoint(params.get("endPoint"));
            route.setName(params.get("name"));
            route.setStatus(params.get("status"));
            route.setFrequency(params.get("frequency"));
            route.setDistance(Float.parseFloat(params.get("distance")));
            route.setDuration(Integer.parseInt(params.get("duration")));
            return routeRepository.updateRoute(route);
        }
        return null;
    }

    @Override
    public Route getRouteById(int id) {
        return routeRepository.getRouteById(id);
    }

    @Override
    public void deleteRoute(int id) {
        routeRepository.deleteRoute(id);
    }

    @Override
    public List<Station> getStationsByCoordinates(Map<String, String> params) {
        return routeRepository.getStationsByCoordinates(params);
    }

    @Override
    public List<RouteStation> getRouteStations(int routeId) {
        return routeRepository.getRouteStations(routeId);
    }

    @Override
    public List<Route> getRoutes(Map<String, String> params) {
        return this.routeRepository.getRoutes(params);
    }

    @Override
    public List<Route> findRoutesByStations(int departureStationId, int arrivalStationId) {
        return routeRepository.findRoutesByStations(departureStationId, arrivalStationId);
    }
    
}
