package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.mapper.AdminUserMapper;
import com.quinhat.mapper.AdminUserNotificationMapper;
import com.quinhat.mapper.AdminVehicleMapper;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.pojo.UserNotification;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.UserNotificationRepository;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
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
    public List<AdminUserNotificationDTO> getAllUserNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        List<UserNotification> userNotifications = s.createQuery("FROM UserNotification un", UserNotification.class).getResultList();
        return userNotifications.stream()
                .map(AdminUserNotificationMapper::toDTO)
                .toList();
    }

    @Override
    public List<UserNotification> getNotificationsByUserId(Integer userId) {
        // Sử dụng Hibernate Session để truy vấn
        Session session = this.factory.getObject().getCurrentSession();

        // Viết câu lệnh HQL để lấy danh sách UserNotification cho userId
        String hql = """
            SELECT un
            FROM UserNotification un
            JOIN un.notificationId n
            WHERE un.userId.id = :userId
            ORDER BY un.sendAt DESC
        """;

        Query<UserNotification> query = session.createQuery(hql, UserNotification.class
        );
        query.setParameter("userId", userId);

        // Trả về danh sách UserNotification
        return query.getResultList();
    }

    @Override
    public UserNotification create(UserNotification u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);

        return u;
    }

    @Override
    public void save(UserNotification un) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(un);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM UserNotification u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminUserNotificationDTO> getUserNotificationsPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<UserNotification> list = s.createQuery("FROM UserNotification u", UserNotification.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminUserNotificationMapper::toDTO).toList();
    }

    @Override
    public long countUserNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(u.id) FROM UserNotification u", Long.class).getSingleResult();
    }

    @Override
    public void update(UserNotification un) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(un);
    }

    @Override
    public UserNotification findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "FROM UserNotification v "
                + "JOIN FETCH v.notificationId "
                + "JOIN FETCH v.userId "
                + "WHERE v.id = :id";
        return s.createQuery(hql, UserNotification.class)
                .setParameter("id", id)
                .uniqueResult();
    }

}
