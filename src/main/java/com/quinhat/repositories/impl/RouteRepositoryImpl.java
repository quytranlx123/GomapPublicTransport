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
}
