/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RoutePathDTO {

    private Route route;
    private List<RouteStation> routeStations;

    public RoutePathDTO(Route route, List<RouteStation> routeStations) {
        this.route = route;
        this.routeStations = routeStations;
    }

    // Getters và Setters
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<RouteStation> getRouteStations() {
        return routeStations;
    }

    public void setRouteStations(List<RouteStation> routeStations) {
        this.routeStations = routeStations;
    }
}
