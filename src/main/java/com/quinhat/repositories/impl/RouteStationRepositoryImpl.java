package com.quinhat.repositories.impl;

import com.quinhat.pojo.RouteStation;

import com.quinhat.repositories.RouteStationRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RouteStationRepositoryImpl implements RouteStationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<RouteStation> getAllRouteStations() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM RouteStation", RouteStation.class).getResultList();
    }

    @Override
    public void save(RouteStation routeStation) {
        Session s = this.factory.getObject().getCurrentSession();
        if (routeStation.getId() == null) {
            s.persist(routeStation);
        } else {
            s.merge(routeStation);
        }
    }
}
