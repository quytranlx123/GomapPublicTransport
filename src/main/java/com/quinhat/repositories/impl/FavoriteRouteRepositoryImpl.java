package com.quinhat.repositories.impl;

import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.repositories.FavoriteRouteRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class FavoriteRouteRepositoryImpl implements FavoriteRouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<FavoriteRoute> getAllFavoriteRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM FavoriteRoute", FavoriteRoute.class).getResultList();
    }

    @Override
    public void save(FavoriteRoute favoriteRoute) {
        Session s = this.factory.getObject().getCurrentSession();
        if (favoriteRoute.getId() == null) {
            s.persist(favoriteRoute);
        } else {
            s.merge(favoriteRoute);
        }
    }
}
