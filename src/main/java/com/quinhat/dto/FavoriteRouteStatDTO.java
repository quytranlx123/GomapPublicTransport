/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

/**
 *
 * @author tranngocqui
 */
public class FavoriteRouteStatDTO {

    private String routeName;   // Tên tuyến
    private Long count;       // Tổng lượt yêu thích

    public FavoriteRouteStatDTO(String routeName, Long count) {
        this.routeName = routeName;
        this.count = count;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
