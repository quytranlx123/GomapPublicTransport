/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto.Response;

import com.quinhat.dto.AdminRouteFavoriteCountDTO;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public class AdminStatisticsResponseDTO {

    private List<AdminRouteFavoriteCountDTO> top5Routes;

    public AdminStatisticsResponseDTO(List<AdminRouteFavoriteCountDTO> top5Routes) {
        this.top5Routes = top5Routes;
    }

    // getter, setter
    public List<AdminRouteFavoriteCountDTO> getTop5Routes() {
        return top5Routes;
    }

    public void setTop5Routes(List<AdminRouteFavoriteCountDTO> top5Routes) {
        this.top5Routes = top5Routes;
    }
}
