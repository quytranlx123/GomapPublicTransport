/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.dto;

/**
 *
 * @author tranngocqui
 */
// DTO cho mỗi tuyến
public class AdminRouteFavoriteCountDTO {

    private String routeName;
    private int count;

    public AdminRouteFavoriteCountDTO(String routeName, int count) {
        this.routeName = routeName;
        this.count = count;
    }

    // getter, setter
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
