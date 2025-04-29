/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.repositories.TrafficReportRepository;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.TrafficReportService;
import com.quinhat.services.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASUS
 */
@Service
public class TrafficReportServiceImpl implements TrafficReportService {

    @Autowired
    private TrafficReportRepository trafficReportRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<TrafficReport> getTrafficReports(Map<String, String> params) {
        return this.trafficReportRepo.getTrafficReports(params);
    }

    @Override
    public TrafficReport createTrafficReport(Map<String, String> params, MultipartFile image, User user) {
        TrafficReport report = new TrafficReport();

        report.setTitle(params.get("title"));
        report.setAddress(params.get("address"));
        report.setDescription(params.get("description"));
        report.setCreatedAt(new Date());

        try {
            report.setLatitude(Float.parseFloat(params.get("latitude")));
            report.setLongitude(Float.parseFloat(params.get("longitude")));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Latitude hoặc Longitude không hợp lệ");
        }

        report.setUserId(user); // ✨ Đây là phần quan trọng

        if (image != null && !image.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                report.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(TrafficReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.trafficReportRepo.createTrafficReport(report);
    }

    @Override
    public TrafficReport getTrafficReportById(int id) {
        return this.trafficReportRepo.getTrafficReportById(id);
    }

    @Override
    public void deleteTrafficReport(int id) {
        this.trafficReportRepo.deleteTrafficReport(id);
    }

    @Override
    public TrafficReport updateTrafficReport(int id, Map<String, String> params, MultipartFile image) {
        TrafficReport report = this.trafficReportRepo.getTrafficReportById(id);

        if (report == null) {
            throw new IllegalArgumentException("Không tìm thấy phản ánh!");
        }

        if (params.containsKey("title")) {
            report.setTitle(params.get("title"));
        }
        if (params.containsKey("address")) {
            report.setAddress(params.get("address"));
        }
        if (params.containsKey("description")) {
            report.setDescription(params.get("description"));
        }

        if (params.containsKey("latitude")) {
            report.setLatitude(Float.parseFloat(params.get("latitude")));
        }
        if (params.containsKey("longitude")) {
            report.setLongitude(Float.parseFloat(params.get("longitude")));
        }

        if (image != null && !image.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                report.setImage(uploadResult.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(TrafficReportServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.trafficReportRepo.updateTrafficReport(report);
    }

    @Override
    public List<TrafficReport> getAllTrafficReports() {
        return trafficReportRepo.getAllTrafficReports();
    }

    @Override
    public void save(TrafficReport trafficReport) {
        trafficReportRepo.save(trafficReport);
    }

}
