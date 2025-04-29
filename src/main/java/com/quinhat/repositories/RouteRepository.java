package com.quinhat.repositories;

import com.quinhat.pojo.Route;
import java.util.List;

public interface RouteRepository {

    List<Route> getAllRoutes();

    void save(Route route);

    Route getRouteById(int id);

}
