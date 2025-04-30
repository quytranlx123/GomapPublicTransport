package com.quinhat.repositories;

import com.quinhat.pojo.RouteStation;
import java.util.List;

public interface RouteStationRepository {

    //Qui
    List<RouteStation> getAllRouteStations();

    void save(RouteStation routeStation);
    //qui

    List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId);

}
