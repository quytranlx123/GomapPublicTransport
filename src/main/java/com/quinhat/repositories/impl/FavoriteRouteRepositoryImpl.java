package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.dto.FavoriteRouteStatDTO;
import com.quinhat.mapper.AdminFavoriteRouteMapper;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.User;
import com.quinhat.repositories.FavoriteRouteRepository;
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
public class FavoriteRouteRepositoryImpl implements FavoriteRouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<AdminFavoriteRouteDTO> getAllFavoriteRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        List<FavoriteRoute> favoriteRoutes = s.createQuery("FROM FavoriteRoute fr", FavoriteRoute.class).getResultList();
        return favoriteRoutes.stream()
                .map(AdminFavoriteRouteMapper::toDTO)
                .toList();
    }

    @Override
    public List<FavoriteRoute> getFavoriteRoutesByUserId(Integer userId) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM FavoriteRoute fr WHERE fr.userId.id = :userId", FavoriteRoute.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void create(Integer userId, Integer routeId) {
        Session session = this.factory.getObject().getCurrentSession();

        User user = session.get(User.class, userId);
        Route route = session.get(Route.class, routeId);

        if (user == null || route == null) {
            throw new IllegalArgumentException("User or Route not found");
        }

        // Check if a FavoriteRoute with the same userId and routeId already exists
        String hql = "FROM FavoriteRoute WHERE userId.id = :userId AND routeId.id = :routeId";
        FavoriteRoute existingFavoriteRoute = session.createQuery(hql, FavoriteRoute.class)
                .setParameter("userId", userId)
                .setParameter("routeId", routeId)
                .uniqueResult();

        if (existingFavoriteRoute != null) {
            throw new IllegalArgumentException("FavoriteRoute with this userId and routeId already exists");
        }

        FavoriteRoute favoriteRoute = new FavoriteRoute();
        favoriteRoute.setUserId(user);
        favoriteRoute.setRouteId(route);

        session.persist(favoriteRoute);
    }

    @Override
    public void delete(Integer favoriteRouteId) {
        Session s = this.factory.getObject().getCurrentSession();
        FavoriteRoute favoriteRoute = s.get(FavoriteRoute.class, favoriteRouteId);
        if (favoriteRoute != null) {
            s.remove(favoriteRoute);
        }
    }

    @Override
    public void save(AdminFavoriteRouteDTO dto) {
        Session session = this.factory.getObject().getCurrentSession();

        // Lấy User và Route từ DB để đảm bảo tồn tại
        User user = session.get(User.class, dto.getUserId());
        Route route = session.get(Route.class, dto.getRouteId());

        if (user == null || route == null) {
            throw new IllegalArgumentException("User hoặc Route không tồn tại");
        }

        // Dùng mapper để tạo entity từ DTO (nếu mapper không set user/route)
        FavoriteRoute favoriteRoute = AdminFavoriteRouteMapper.toEntity(dto);

        // Gắn thực thể User và Route vào entity
        favoriteRoute.setUserId(user);
        favoriteRoute.setRouteId(route);

        // Gán thời gian nếu chưa có
        if (favoriteRoute.getCreatedAt() == null) {
            favoriteRoute.setCreatedAt(new java.util.Date());
        }

        session.persist(favoriteRoute);

    }

    @Override
    public List<Object[]> getTop5FavoriteRoutes() {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = "SELECT fr.routeId.name, COUNT(fr.id) "
                + "FROM FavoriteRoute fr "
                + "GROUP BY fr.routeId.name "
                + "ORDER BY COUNT(fr.id) DESC";

        return s.createQuery(hql, Object[].class)
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public void adminDelete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM FavoriteRoute u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminFavoriteRouteDTO> getFavoriteRoutesPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<FavoriteRoute> list = s.createQuery("FROM FavoriteRoute f", FavoriteRoute.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminFavoriteRouteMapper::toDTO).toList();
    }

    @Override
    public long countFavoriteRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(f.id) FROM FavoriteRoute f", Long.class).getSingleResult();
    }

    @Override
    public void update(FavoriteRoute f) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(f);
    }

    @Override
    public FavoriteRoute findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(FavoriteRoute.class, id);
    }
}
