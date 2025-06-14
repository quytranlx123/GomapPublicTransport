package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.mapper.AdminScheduleMapper;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Schedule;
import com.quinhat.pojo.Vehicle;
import com.quinhat.repositories.ScheduleRepository;
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
public class ScheduleRepositoryImpl implements ScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<AdminScheduleDTO> getAllSchedules() {
        Session s = this.factory.getObject().getCurrentSession();
        List<Schedule> schedules = s.createQuery("FROM Schedule sc", Schedule.class).getResultList();
        return schedules.stream()
                .map(AdminScheduleMapper::toDTO)
                .toList();
    }

    @Override
    public List<Schedule> getSchedulesByRouteId(int routeId, int page, int pageSize) {
        Session session = this.factory.getObject().getCurrentSession();

        // Truy vấn Schedule, JOIN FETCH để lấy luôn Vehicle và so sánh theo v.routeId.id
        String hql = "SELECT s FROM Schedule s "
                + "JOIN FETCH s.vehicleId v "
                + "WHERE v.routeId.id = :routeId "
                + "ORDER BY s.id";

        Query<Schedule> query = session.createQuery(hql, Schedule.class);
        query.setParameter("routeId", routeId);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public long countSchedulesByRouteId(int routeId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT COUNT(s) FROM Schedule s JOIN s.vehicleId v WHERE v.routeId.id = :routeId";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("routeId", routeId);

        return query.getSingleResult();
    }

    @Override
    public List<Schedule> getSchedulesByVehicleId(int vehicleId, int page, int pageSize) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT s FROM Schedule s "
                + "WHERE s.vehicleId.id = :vehicleId "
                + "ORDER BY s.id";

        Query<Schedule> query = session.createQuery(hql, Schedule.class);
        query.setParameter("vehicleId", vehicleId);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public long countSchedulesByVehicleId(int vehicleId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT COUNT(s) FROM Schedule s WHERE s.vehicleId.id = :vehicleId";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("vehicleId", vehicleId);

        return query.getSingleResult();
    }

    @Override
    public void save(AdminScheduleDTO dto) {
        Session session = this.factory.getObject().getCurrentSession();

        Vehicle vehicle = session.get(Vehicle.class, dto.getVehicleId());

        if (vehicle == null) {
            throw new IllegalArgumentException("Phương tiện không tồn tại");
        }
        Schedule schedule = AdminScheduleMapper.toEntity(dto, vehicle);
        session.persist(schedule);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM Schedule u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminScheduleDTO> getSchedulesPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Schedule> list = s.createQuery("FROM Schedule s", Schedule.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminScheduleMapper::toDTO).toList();
    }

    @Override
    public long countSchedules() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(s.id) FROM Schedule s", Long.class).getSingleResult();
    }

    @Override
    public void update(Schedule sc) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(sc);
    }

    @Override
    public Schedule findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Schedule.class, id);
    }
}
