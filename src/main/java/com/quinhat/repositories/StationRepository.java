package com.quinhat.repositories;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.List;
import java.util.Map;

public interface StationRepository {

    //QUi
    List<AdminStationDTO> getAllStations();

    void save(AdminStationDTO dto);

    void delete(List<Integer> ids);

    List<AdminStationDTO> getStationsPaginated(int page, int size);

    long countStations();

    void update(Station st);

    Station findById(int id);

    //Qui
    List<Station> findNearestStations(float latitude, float longitude, int limit);

    List<Station> getStations(Map<String, String> params);

    List<RouteStation> getStationsByRouteId(int routeId, int page, int pageSize);

    long countStationsByRouteId(int routeId);

}
