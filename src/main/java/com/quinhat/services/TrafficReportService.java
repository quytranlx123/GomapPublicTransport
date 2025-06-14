/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminStationDTO;
import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASUS
 */
public interface TrafficReportService {

//    Qui
    List<AdminTrafficReportDTO> getAllTrafficReports();

    void save(AdminTrafficReportDTO dto);

    void delete(List<Integer> ids);

    List<AdminTrafficReportDTO> getTrafficReportsPaginated(int page, int size);

    long countTrafficReports();

    TrafficReport findById(int id);

    AdminTrafficReportDTO update(AdminTrafficReportDTO dto, MultipartFile newImageFile);

//    Qui
    List<TrafficReport> getTrafficReports(Map<String, String> params, int page, int pageSize);

    TrafficReport createTrafficReport(Map<String, String> params, MultipartFile image, User user);

    TrafficReport updateTrafficReport(int id, Map<String, String> params, MultipartFile image);

    TrafficReport getTrafficReportById(int id);

    void deleteTrafficReport(int id);

    Map<String, Long> countTrafficReportsByMonth(int month);

    long countTrafficReportsByUserId(int userId, String type);

    List<TrafficReport> getVerifiedReports();
}
