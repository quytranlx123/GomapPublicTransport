/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.Notification;
import java.util.List;

/**
 *
 * @author tranngocqui
 */
public interface NotificationService {

    public List<Notification> getAllNotifications();

    void save(Notification notification);

}
