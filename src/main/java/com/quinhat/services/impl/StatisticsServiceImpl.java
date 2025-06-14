/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.quinhat.dto.AdminRouteFavoriteCountDTO;
import com.quinhat.services.StatisticsService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author tranngocqui
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public List<AdminRouteFavoriteCountDTO> getTop5FavoriteRoutes() {
        // Thay bằng logic thật lấy dữ liệu từ DB
        return Arrays.asList(
                new AdminRouteFavoriteCountDTO("Tuyến 1", 120),
                new AdminRouteFavoriteCountDTO("Tuyến 2", 90),
                new AdminRouteFavoriteCountDTO("Tuyến 3", 75),
                new AdminRouteFavoriteCountDTO("Tuyến 4", 60),
                new AdminRouteFavoriteCountDTO("Tuyến 5", 50)
        );
    }

}
