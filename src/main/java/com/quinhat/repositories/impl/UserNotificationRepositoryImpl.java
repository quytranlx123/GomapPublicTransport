package com.quinhat.repositories.impl;

import com.quinhat.pojo.UserNotification;
import com.quinhat.repositories.UserNotificationRepository;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserNotificationRepositoryImpl implements UserNotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<UserNotification> getAllUserNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM UserNotification", UserNotification.class).getResultList();
    }
}
