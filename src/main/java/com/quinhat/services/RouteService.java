/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.Route;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface RouteService {

    public List<Route> getAllRoutes();

    void save(Route route);

    Route getRouteById(int id);

}
