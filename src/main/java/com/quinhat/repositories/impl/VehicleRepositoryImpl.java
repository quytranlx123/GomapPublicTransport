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
import org.hibernate.query.Query;
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

    @Override
    public void save(Vehicle vehicle) {
        Session s = this.factory.getObject().getCurrentSession();
        if (vehicle.getId() == null) {
            s.persist(vehicle);
        } else {
            s.merge(vehicle);
        }
    }

    @Override
    public List<Vehicle> getVehiclesByRouteId(int routeId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "FROM Vehicle v WHERE v.routeId.id = :routeId AND v.isActive = true";

        Query<Vehicle> query = session.createQuery(hql, Vehicle.class);
        query.setParameter("routeId", routeId);

        return query.getResultList();
    }

}
