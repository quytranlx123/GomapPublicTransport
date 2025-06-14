/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminRouteFavoriteCountDTO;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface StatisticsService {

    public List<AdminRouteFavoriteCountDTO> getTop5FavoriteRoutes();
}
