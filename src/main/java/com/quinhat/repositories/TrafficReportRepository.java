/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.pojo.TrafficReport;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public interface TrafficReportRepository {

    List<TrafficReport> getTrafficReports(Map<String, String> params);

    TrafficReport createTrafficReport(TrafficReport p);
    
    TrafficReport updateTrafficReport(TrafficReport p);

    TrafficReport getTrafficReportById(int id);

    void deleteTrafficReport(int id);
   
}
