/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.pojo.RouteStation;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface RouteStationService {

    public List<AdminRouteStationDTO> getAllRouteStations();

    void save(AdminRouteStationDTO dto);

    void delete(List<Integer> ids);

    RouteStation findById(int id);

    AdminRouteStationDTO update(AdminRouteStationDTO dto);

    List<AdminRouteStationDTO> getRouteStationsPaginated(int page, int size);

    long countRouteStations();

    List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId);

}
