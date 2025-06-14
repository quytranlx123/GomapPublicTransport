package com.quinhat.repositories;

import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteStatDTO;
import com.quinhat.pojo.FavoriteRoute;
import java.util.List;

public interface FavoriteRouteRepository {
    //qui

    List<AdminFavoriteRouteDTO> getAllFavoriteRoutes();

    void save(AdminFavoriteRouteDTO dto);

    void adminDelete(List<Integer> ids);

    List<AdminFavoriteRouteDTO> getFavoriteRoutesPaginated(int page, int size);

    long countFavoriteRoutes();

    void update(FavoriteRoute f);

    FavoriteRoute findById(int id);

    //qui
    List<FavoriteRoute> getFavoriteRoutesByUserId(Integer userId);

    void create(Integer userId, Integer routeId);

    void delete(Integer favoriteRouteId);

    List<Object[]> getTop5FavoriteRoutes();

}
