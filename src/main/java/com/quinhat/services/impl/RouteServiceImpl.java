package com.quinhat.services.impl;

import com.quinhat.pojo.Route;
import com.quinhat.repositories.RouteRepository;
import com.quinhat.services.RouteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepo;

    @Override
    public List<Route> getAllRoutes() {
        return routeRepo.getAllRoutes();
    }

    @Override
    public void save(Route route) {
        routeRepo.save(route);
    }

    @Override
    public Route getRouteById(int id) {
        return routeRepo.getRouteById(id);
    }
}
