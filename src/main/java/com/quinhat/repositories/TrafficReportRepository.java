/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.pojo.TrafficReport;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public interface TrafficReportRepository {

//    Qui
    List<AdminTrafficReportDTO> getAllTrafficReports();

    void save(AdminTrafficReportDTO dto);

    void delete(List<Integer> ids);

    List<AdminTrafficReportDTO> getTrafficReportsPaginated(int page, int size);

    long countTrafficReports();

    void update(TrafficReport u);

    TrafficReport findById(int id);

//    Qui
    List<TrafficReport> getTrafficReports(Map<String, String> params, int page, int pageSize);

    TrafficReport createTrafficReport(TrafficReport p);

    TrafficReport updateTrafficReport(TrafficReport p);

    TrafficReport getTrafficReportById(int id);

    void deleteTrafficReport(int id);

    List<Object[]> countTrafficReportsByMonth(int month);

    long countTrafficReportsByUserId(int userId, String type);
    
    List<TrafficReport> getVerifiedReports();

}
