/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

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

    List<TrafficReport> getTrafficReports(Map<String, String> params);

    TrafficReport createTrafficReport(Map<String, String> params, MultipartFile image, User user);

    TrafficReport updateTrafficReport(int id, Map<String, String> params, MultipartFile image);

    TrafficReport getTrafficReportById(int id);

    void deleteTrafficReport(int id);
}
