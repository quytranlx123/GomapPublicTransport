/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.repositories.impl;

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
    public List<TrafficReport> getTrafficReports(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<TrafficReport> q = b.createQuery(TrafficReport.class);
        Root root = q.from(TrafficReport.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

//            String kw = params.get("kw");
//            if (kw != null && !kw.isEmpty()) {
//                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
//            }
            String userId = params.get("userId");
            if (userId != null && !userId.isEmpty()) {
                predicates.add(b.equal(root.get("userId").as(Integer.class),
                        userId));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int start = (page - 1) * PAGE_SIZE;

            query.setFirstResult(start);
            query.setMaxResults(PAGE_SIZE);
        }

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
    public List<TrafficReport> getAllTrafficReports() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM TrafficReport", TrafficReport.class).getResultList();
    }

}
