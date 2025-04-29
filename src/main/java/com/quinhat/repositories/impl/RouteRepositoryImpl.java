package com.quinhat.repositories.impl;

import com.quinhat.pojo.Route;
import com.quinhat.repositories.RouteRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RouteRepositoryImpl implements RouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Route> getAllRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM Route", Route.class).getResultList();
    }

    @Override
    public void save(Route route) {
        Session s = this.factory.getObject().getCurrentSession();
        if (route.getId() == null) {
            s.persist(route);
        } else {
            s.merge(route);
        }
    }

    @Override
    public Route getRouteById(int id) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(Route.class, id);
    }
}
