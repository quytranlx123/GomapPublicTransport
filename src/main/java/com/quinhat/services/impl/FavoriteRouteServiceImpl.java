package com.quinhat.services.impl;

import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.repositories.FavoriteRouteRepository;
import com.quinhat.services.FavoriteRouteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteRouteServiceImpl implements FavoriteRouteService {

    @Autowired
    private FavoriteRouteRepository favoriteRouteRepo;

    @Override
    public List<FavoriteRoute> getAllFavoriteRoutes() {
        return favoriteRouteRepo.getAllFavoriteRoutes();
    }

    @Override
    public void save(FavoriteRoute favoriteRoute) {
        favoriteRouteRepo.save(favoriteRoute);
    }
}
