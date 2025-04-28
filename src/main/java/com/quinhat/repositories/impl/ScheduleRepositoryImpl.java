package com.quinhat.repositories.impl;

import com.quinhat.pojo.Schedule;
import com.quinhat.repositories.ScheduleRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ScheduleRepositoryImpl implements ScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Schedule> getAllSchedules() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM Schedule", Schedule.class).getResultList();
    }
}
