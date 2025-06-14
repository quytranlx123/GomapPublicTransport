/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.mapper.AdminFavoriteRouteMapper;
import com.quinhat.pojo.User;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.VehicleRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.quinhat.mapper.AdminVehicleMapper;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Route;
import com.quinhat.services.RouteService;
import java.util.Date;
import org.hibernate.query.MutationQuery;

/**
 *
 * @author tranngocqui
 */
@Repository
@Transactional
public class VehicleRepositoryImpl implements VehicleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private RouteService routeService;

    @Override
    public List<AdminVehicleDTO> getAllVehicles() {
        Session s = this.factory.getObject().getCurrentSession();
        List<Vehicle> vehicles = s.createQuery("FROM Vehicle v", Vehicle.class).getResultList();
        return vehicles.stream()
                .map(AdminVehicleMapper::toDTO)
                .toList();
    }

    @Override
    public List<Vehicle> getVehiclesByRouteId(int routeId, int page, int pageSize) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "FROM Vehicle v WHERE v.routeId.id = :routeId AND v.isActive = true";

        Query<Vehicle> query = session.createQuery(hql, Vehicle.class);
        query.setParameter("routeId", routeId);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public long countVehiclesByRouteid(int routeId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT COUNT(v) FROM Vehicle v WHERE v.routeId.id = :routeId";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("routeId", routeId);

        return query.getSingleResult();
    }

    @Override
    public void save(AdminVehicleDTO dto) {
        Session session = this.factory.getObject().getCurrentSession();

        // Lấy Route từ DB để đảm bảo tồn tại
        Route route = session.get(Route.class, dto.getRouteId());
        if (route == null) {
            throw new IllegalArgumentException("Route không tồn tại với ID = " + dto.getRouteId());
        }

        Vehicle vehicle = AdminVehicleMapper.toEntity(dto, route);

        // Gán thời gian mặc định nếu chưa có
        Date now = new Date();
        if (vehicle.getCreatedAt() == null) {
            vehicle.setCreatedAt(now);
        }
        if (vehicle.getUpdatedAt() == null) {
            vehicle.setUpdatedAt(now);
        }

        session.persist(vehicle);
    }

    @Override
    public long countByIsActive(boolean isActive) {
        Session session = this.factory.getObject().getCurrentSession();
        String hql = "SELECT COUNT(v) FROM Vehicle v WHERE v.isActive = :isActive";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("isActive", isActive);
        return query.getSingleResult();
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM Vehicle u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminVehicleDTO> getVehiclesPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Vehicle> vehicles = s.createQuery("FROM Vehicle v", Vehicle.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return vehicles.stream().map(AdminVehicleMapper::toDTO).toList();
    }

    @Override
    public long countVehicles() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(v.id) FROM Vehicle v", Long.class).getSingleResult();
    }

    @Override
    public void update(Vehicle v) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(v);
    }

    @Override
    public Vehicle findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        String hql = "FROM Vehicle v LEFT JOIN FETCH v.routeId WHERE v.id = :id";
        return s.createQuery(hql, Vehicle.class)
                .setParameter("id", id)
                .uniqueResult();
    }

}
