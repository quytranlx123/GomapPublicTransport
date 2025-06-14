package com.quinhat.repositories;

import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.pojo.RouteStation;
import java.util.List;

public interface RouteStationRepository {

    //Qui
    List<AdminRouteStationDTO> getAllRouteStations();

    void save(AdminRouteStationDTO dto);

    void delete(List<Integer> ids);

    List<AdminRouteStationDTO> getRouteStationsPaginated(int page, int size);

    long countRouteStations();

    void update(RouteStation r);

    RouteStation findById(int id);

    //qui
    List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId);

}
