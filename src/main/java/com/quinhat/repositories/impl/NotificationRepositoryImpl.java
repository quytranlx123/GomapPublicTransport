package com.quinhat.repositories.impl;

import com.quinhat.pojo.Notification;
import com.quinhat.repositories.NotificationRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Notification> getAllNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM Notification", Notification.class).getResultList();
    }

    @Override
    public void save(Notification notification) {
        Session s = this.factory.getObject().getCurrentSession();
        if (notification.getId() == null) {
            s.persist(notification);
        } else {
            s.merge(notification);
        }
    }
}
