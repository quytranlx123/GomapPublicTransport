/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.mapper.AdminTrafficReportMapper;
import com.quinhat.pojo.Station;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.repositories.TrafficReportRepository;
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

/**
 *
 * @author ASUS
 */
@Repository
@Transactional
public class TrafficReportRepositoryImpl implements TrafficReportRepository {

    private static final int PAGE_SIZE = 20;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<TrafficReport> getTrafficReports(Map<String, String> params, int page, int pageSize) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<TrafficReport> q = b.createQuery(TrafficReport.class);
        Root root = q.from(TrafficReport.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String userId = params.get("userId");
            if (userId != null && !userId.isEmpty()) {
                predicates.add(b.equal(root.get("userId").as(Integer.class),
                        userId));
            }

            String type = params.get("type");
            if (type != null && !type.isEmpty()) {
                try {
                    TrafficReport.ReportType enumType = TrafficReport.ReportType.valueOf(type.toLowerCase());
                    predicates.add(b.equal(root.get("type"), enumType));
                } catch (IllegalArgumentException e) {
                    // Nếu type không hợp lệ thì không lọc theo type
                    System.out.println("Invalid ReportType: " + type);
                }
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        q.orderBy(b.desc(root.get("createdAt")));

        Query query = s.createQuery(q);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public TrafficReport createTrafficReport(TrafficReport p) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(p);

        return p;
    }

    @Override
    public TrafficReport getTrafficReportById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(TrafficReport.class, id);
    }

    @Override
    public void deleteTrafficReport(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        TrafficReport p = this.getTrafficReportById(id);
        s.remove(p);
    }

    @Override
    public TrafficReport updateTrafficReport(TrafficReport p) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(p);

        return p;
    }

    @Override
    public List<AdminTrafficReportDTO> getAllTrafficReports() {
        Session s = this.factory.getObject().getCurrentSession();
        List<TrafficReport> trafficReports = s
                .createQuery("FROM TrafficReport tr ORDER BY tr.isVerified DESC", TrafficReport.class)
                .getResultList();

        return trafficReports.stream()
                .map(AdminTrafficReportMapper::toDTO)
                .toList();

    }

    @Override
    public void save(AdminTrafficReportDTO dto) {
        Session s = this.factory.getObject().getCurrentSession();
        if (dto.getId() == null) {
            s.persist(dto);
        } else {
            s.merge(dto);
        }
    }

    @Override
    public List<Object[]> countTrafficReportsByMonth(int month) {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = """
        SELECT tr.isVerified, COUNT(tr)
        FROM TrafficReport tr
        WHERE MONTH(tr.createdAt) = :month
        GROUP BY tr.isVerified
        """;

        Query<Object[]> query = s.createQuery(hql, Object[].class);
        query.setParameter("month", month);

        return query.getResultList();
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM TrafficReport u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public long countTrafficReportsByUserId(int userId, String type) {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = "SELECT COUNT(tr) FROM TrafficReport tr WHERE tr.userId.id = :userId";

        boolean hasValidType = false;
        TrafficReport.ReportType enumType = null;

        if (type != null && !type.isEmpty()) {
            try {
                enumType = TrafficReport.ReportType.valueOf(type.toLowerCase());
                hql += " AND tr.type = :type";
                hasValidType = true;
            } catch (IllegalArgumentException e) {
                // Không thêm điều kiện type nếu không hợp lệ
                System.out.println("Invalid ReportType: " + type);
            }
        }

        Query<Long> query = s.createQuery(hql, Long.class);
        query.setParameter("userId", userId);

        if (hasValidType) {
            query.setParameter("type", enumType);
        }

        return query.getSingleResult();
    }

    @Override
    public List<AdminTrafficReportDTO> getTrafficReportsPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<TrafficReport> list = s.createQuery("FROM TrafficReport t", TrafficReport.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminTrafficReportMapper::toDTO).toList();
    }

    @Override
    public long countTrafficReports() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(t.id) FROM TrafficReport t", Long.class).getSingleResult();
    }

    @Override
    public void update(TrafficReport t) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(t);
    }

    @Override
    public TrafficReport findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(TrafficReport.class, id);
    }

    @Override
    public List<TrafficReport> getVerifiedReports() {
        Session s = this.factory.getObject().getCurrentSession();

        String hql = """
        FROM TrafficReport tr
        WHERE tr.type = 'report' AND tr.isVerified = true
        """;

        Query<TrafficReport> query = s.createQuery(hql, TrafficReport.class);
        return query.getResultList();
    }
}
