package com.quinhat.services.impl;

import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteStatDTO;
import com.quinhat.mapper.AdminFavoriteRouteMapper;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.User;
import com.quinhat.repositories.FavoriteRouteRepository;
import com.quinhat.repositories.RouteRepository;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.FavoriteRouteService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteRouteServiceImpl implements FavoriteRouteService {

    @Autowired
    private FavoriteRouteRepository favoriteRouteRepo;
    @Autowired
    private RouteRepository routeRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<FavoriteRoute> getFavoriteRoutesByUserId(Integer userId) {
        return favoriteRouteRepo.getFavoriteRoutesByUserId(userId);
    }

    @Override
    public void create(Integer userId, Integer routeId) {
        favoriteRouteRepo.create(userId, routeId);
    }

    @Override
    public void delete(Integer favoriteRouteId) {
        favoriteRouteRepo.delete(favoriteRouteId);
    }

    @Override
    public List<AdminFavoriteRouteDTO> getAllFavoriteRoutes() {
        return favoriteRouteRepo.getAllFavoriteRoutes();
    }

    @Override
    public void save(AdminFavoriteRouteDTO dto) {
        favoriteRouteRepo.save(dto);
    }

    @Override
    public List<Object[]> getTop5FavoriteRoutes() {
        return favoriteRouteRepo.getTop5FavoriteRoutes();
    }
    
//    @Override
//    public List<FavoriteRouteStatDTO> getTop5FavoriteRoutes() {
//        return favoriteRouteRepo.getTop5FavoriteRoutes();
//    }

    @Override
    public void delete(List<Integer> ids) {
        favoriteRouteRepo.adminDelete(ids);
    }

    @Override
    public List<AdminFavoriteRouteDTO> getFavoriteRoutesPaginated(int page, int size) {
        return favoriteRouteRepo.getFavoriteRoutesPaginated(page, size);
    }

    @Override
    public long countFavoriteRoutes() {
        return favoriteRouteRepo.countFavoriteRoutes();
    }

    @Override
    public FavoriteRoute findById(int id) {
        return favoriteRouteRepo.findById(id);
    }

    @Override
    public AdminFavoriteRouteDTO update(AdminFavoriteRouteDTO dto) {
        FavoriteRoute existing = favoriteRouteRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Favorite route not found");
        }

        if (dto.getRouteId() != null) {
            Route r = routeRepo.findById(dto.getRouteId());
            if (r == null) {
                throw new IllegalArgumentException("Route not found");
            }
            existing.setRouteId(r);
        }
        if (dto.getUserId() != null) {
            User un = userRepo.findById(dto.getUserId());
            if (un == null) {
                throw new IllegalArgumentException("User not found with ID = " + dto.getUserId());
            }
            existing.setUserId(un);
        }
        // Nếu cho phép cập nhật createdAt (không khuyến khích):
        // if (dto.getCreatedAt() != null) existing.setCreatedAt(dto.getCreatedAt());

        favoriteRouteRepo.update(existing);
        return AdminFavoriteRouteMapper.toDTO(existing);
    }

}
