package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.mapper.AdminStationMapper;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.repositories.StationRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StationRepositoryImpl implements StationRepository {

    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<AdminStationDTO> getAllStations() {
        Session s = this.factory.getObject().getCurrentSession();
        List<Station> stations = s.createQuery("FROM Station st", Station.class).getResultList();
        return stations.stream()
                .map(AdminStationMapper::toDTO)
                .toList();
    }

    @Override
    public List<Station> findNearestStations(float latitude, float longitude, int limit) {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = "SELECT st FROM Station st "
                + "ORDER BY (POWER(st.latitude - :latitude, 2) + POWER(st.longitude - :longitude, 2)) ASC";

        Query<Station> query = s.createQuery(hql, Station.class);
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<Station> getStations(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Station> q = b.createQuery(Station.class);
        Root root = q.from(Station.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String routeId = params.get("routeId");
            if (routeId != null && !routeId.isEmpty()) {
                predicates.add(b.equal(root.get("routeId").as(Integer.class), routeId));
            }

            q.where(predicates.toArray(Predicate[]::new));

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

    @Override
    public List<RouteStation> getStationsByRouteId(int routeId, int page, int pageSize) {
        Session s = this.factory.getObject().getCurrentSession();

        // Truy vấn RouteStation, JOIN FETCH để lấy luôn Station tương ứng
        String hql = "SELECT rs FROM RouteStation rs "
                + "JOIN FETCH rs.stationId "
                + "WHERE rs.routeId.id = :routeId "
                + "ORDER BY rs.orderStation ASC";

        Query<RouteStation> query = s.createQuery(hql, RouteStation.class);
        query.setParameter("routeId", routeId);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public long countStationsByRouteId(int routeId) {
        Session session = this.factory.getObject().getCurrentSession();

        String hql = "SELECT COUNT(rs) FROM RouteStation rs WHERE rs.routeId.id = :routeId";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("routeId", routeId);

        return query.getSingleResult();
    }

    @Override
    public void save(AdminStationDTO dto) {
        Session s = this.factory.getObject().getCurrentSession();
        Station station = AdminStationMapper.toEntity(dto);
        s.persist(station);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM Station u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminStationDTO> getStationsPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Station> list = s.createQuery("FROM Station s", Station.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminStationMapper::toDTO).toList();
    }

    @Override
    public long countStations() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(s.id) FROM Station s", Long.class).getSingleResult();
    }

    @Override
    public void update(Station st) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(st);
    }

    @Override
    public Station findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Station.class, id);
    }

}
