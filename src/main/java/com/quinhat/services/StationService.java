/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tranngocqui
 */
public interface StationService {

    //Qui
    public List<AdminStationDTO> getAllStations();

    void save(AdminStationDTO dto);

    void delete(List<Integer> ids);

    List<AdminStationDTO> getStationsPaginated(int page, int size);

    long countStations();

    Station findById(int id);

    AdminStationDTO update(AdminStationDTO dto);

    //Qui
    List<Station> findNearestStations(float latitude, float longitude, int limit);

    List<Station> getStations(Map<String, String> params);

    List<RouteStation> getStationsByRouteId(int routeId, int page, int pageSize);

    long countStationsByRouteId(int routeId);

}
