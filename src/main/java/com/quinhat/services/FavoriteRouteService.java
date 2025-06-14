/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteStatDTO;
import com.quinhat.pojo.FavoriteRoute;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tranngocqui
 */
public interface FavoriteRouteService {

    //Qui
    List<AdminFavoriteRouteDTO> getAllFavoriteRoutes();

    void save(AdminFavoriteRouteDTO dto);

    void delete(List<Integer> ids);

    List<AdminFavoriteRouteDTO> getFavoriteRoutesPaginated(int page, int size);

    long countFavoriteRoutes();

    FavoriteRoute findById(int id);

    AdminFavoriteRouteDTO update(AdminFavoriteRouteDTO dto);

    //Qui
    List<FavoriteRoute> getFavoriteRoutesByUserId(Integer userId
    );

    void create(Integer userId, Integer routeId
    );

    void delete(Integer favoriteRouteId
    );

    List<Object[]> getTop5FavoriteRoutes();
//    List<FavoriteRouteStatDTO> getTop5FavoriteRoutes();

}
