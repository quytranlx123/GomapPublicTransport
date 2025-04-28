/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories.impl;

import com.quinhat.pojo.User;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.VehicleRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tranngocqui
 */
@Repository
@Transactional
public class VehicleRepositoryImpl implements VehicleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Vehicle> getAllVehicles() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM Vehicle", Vehicle.class).getResultList();

    }

}
