package com.quinhat.repositories;

import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.List;
import java.util.Map;

public interface StationRepository {

    //QUi
    List<Station> getAllStations();

    void save(Station station);
    //Qui
    
    
    List<Station> findNearestStations(float latitude, float longitude, int limit);

    List<Station> getStations(Map<String, String> params);

    List<RouteStation> getStationsByRouteId(int routeId);

}
