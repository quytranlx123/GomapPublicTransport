package com.quinhat.repositories.impl;

import com.quinhat.pojo.Station;
import com.quinhat.repositories.StationRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StationRepositoryImpl implements StationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Station> getAllStations() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM Station", Station.class).getResultList();
    }

    @Override
    public void save(Station station) {
        Session s = this.factory.getObject().getCurrentSession();
        if (station.getId() == null) {
            s.persist(station);
        } else {
            s.merge(station);
          
        }
    }
}
