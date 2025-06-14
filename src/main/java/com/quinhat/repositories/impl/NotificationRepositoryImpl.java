package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.mapper.AdminFavoriteRouteMapper;
import com.quinhat.mapper.AdminNotificationMapper;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.User;
import com.quinhat.repositories.NotificationRepository;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<AdminNotificationDTO> getAllNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        List<Notification> notifications = s.createQuery("FROM Notification fr", Notification.class).getResultList();
        return notifications.stream()
                .map(AdminNotificationMapper::toDTO)
                .toList();
    }

    @Override
    public Notification getNotificationById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Notification.class, id);
    }

    @Override
    public void save(AdminNotificationDTO dto) {
        Session session = this.factory.getObject().getCurrentSession();

        // Dùng mapper để tạo entity từ DTO (nếu mapper không set user/route)
        Notification notification = AdminNotificationMapper.toEntity(dto);

        // Gán thời gian nếu chưa có
        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(new java.util.Date());
        }

        session.persist(notification);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM Notification u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminNotificationDTO> getNotificationsPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Notification> list = s.createQuery("FROM Notification n", Notification.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminNotificationMapper::toDTO).toList();
    }

    @Override
    public long countNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(n.id) FROM Notification n", Long.class).getSingleResult();
    }

    @Override
    public void update(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(n);
    }

    @Override
    public Notification findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Notification.class, id);
    }
}
