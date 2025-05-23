/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.FavoriteRoute;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface FavoriteRouteService {

    public List<FavoriteRoute> getAllFavoriteRoutes();

    void save(FavoriteRoute favoriteRoute);

    List<FavoriteRoute> getFavoriteRoutesByUserId(Integer userId);

    void create(Integer userId, Integer routeId);

    void delete(Integer favoriteRouteId);
}
