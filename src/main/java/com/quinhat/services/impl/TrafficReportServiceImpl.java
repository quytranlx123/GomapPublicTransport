/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.mapper.AdminTrafficReportMapper;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.repositories.TrafficReportRepository;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.TrafficReportService;
import com.quinhat.utils.Common;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<TrafficReport> getTrafficReports(Map<String, String> params, int page, int pageSize) {
        return this.trafficReportRepo.getTrafficReports(params, page, pageSize);
    }

    @Override
    public TrafficReport createTrafficReport(Map<String, String> params, MultipartFile image, User user) {
        TrafficReport report = new TrafficReport();

        String title = params.get("title");
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Trường 'title' là bắt buộc.");
        }
        report.setTitle(title.trim());

        String address = params.get("address");
        if (address != null && !address.trim().isEmpty()) {
            report.setAddress(address.trim());
        }

        String description = params.get("description");
        report.setDescription(description != null ? description.trim() : "");

        String typeStr = params.get("type");
        if (typeStr == null || typeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Trường 'type' là bắt buộc.");
        }
        try {
            report.setType(TrafficReport.ReportType.valueOf(typeStr.trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Loại thông báo không hợp lệ: " + typeStr);
        }

        report.setCreatedAt(new Date());
        report.setUserId(user);

        // Xử lý latitude và longitude có thể không truyền vào
        String latStr = params.get("latitude");
        String lonStr = params.get("longitude");

        if (latStr != null && !latStr.trim().isEmpty() && lonStr != null && !lonStr.trim().isEmpty()) {
            try {
                report.setLatitude(Float.parseFloat(latStr.trim()));
                report.setLongitude(Float.parseFloat(lonStr.trim()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Latitude hoặc Longitude không hợp lệ");
            }
        }

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

        if (params.containsKey("type")) {
            try {
                String typeStr = params.get("type").toLowerCase(); // chuyển về chữ thường
                TrafficReport.ReportType reportType = TrafficReport.ReportType.valueOf(typeStr);
                report.setType(reportType);
            } catch (IllegalArgumentException e) {

            }
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
    public List<AdminTrafficReportDTO> getAllTrafficReports() {
        return trafficReportRepo.getAllTrafficReports();
    }

    @Override
    public void save(AdminTrafficReportDTO dto) {
        trafficReportRepo.save(dto);
    }

    @Override
    public Map<String, Long> countTrafficReportsByMonth(int month) {
        List<Object[]> results = this.trafficReportRepo.countTrafficReportsByMonth(month);
        Map<String, Long> resultMap = new HashMap<>();

        // Mặc định là 0 nếu không có
        resultMap.put("Đã xác minh", 0L);
        resultMap.put("Chưa xác minh", 0L);

        for (Object[] row : results) {
            Boolean isVerified = (Boolean) row[0];
            Long count = (Long) row[1];

            if (isVerified != null && isVerified) {
                resultMap.put("Đã xác minh", count);
            } else {
                resultMap.put("Chưa xác minh", count);
            }
        }

        return resultMap;

    }

    @Override
    public void delete(List<Integer> ids) {
        trafficReportRepo.delete(ids);
    }

    @Override
    public long countTrafficReportsByUserId(int userId, String type) {
        return trafficReportRepo.countTrafficReportsByUserId(userId, type);
    }

    @Override
    public List<AdminTrafficReportDTO> getTrafficReportsPaginated(int page, int size) {
        return trafficReportRepo.getTrafficReportsPaginated(page, size);
    }

    @Override
    public long countTrafficReports() {
        return trafficReportRepo.countTrafficReports();
    }

    @Override
    public TrafficReport findById(int id) {
        return trafficReportRepo.findById(id);
    }

    @Override
    public AdminTrafficReportDTO update(AdminTrafficReportDTO dto, MultipartFile newImageFile) {
        TrafficReport existing = trafficReportRepo.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Traffic Report not found");
        }

        // Cập nhật các trường từ dto
        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getAddress() != null) {
            existing.setAddress(dto.getAddress());
        }
        if (dto.getLatitude() != 0) {
            existing.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != 0) {
            existing.setLongitude(dto.getLongitude());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId());
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
            existing.setUserId(user);
        }
        if (dto.getType() != null) {
            try {
                existing.setType(TrafficReport.ReportType.valueOf(dto.getType().toLowerCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid type");
            }
        }
        existing.setIsVerified(dto.isVerified());

        // Xử lý upload ảnh nếu có
        if (newImageFile != null && !newImageFile.isEmpty()) {
            try {
                // Xóa avatar cũ nếu có
                if (existing.getImage() != null && !existing.getImage().isBlank()) {
                    String publicId = Common.extractPublicIdFromUrl(existing.getImage());
                    if (publicId != null) {
                        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    }
                }
                // Upload avatar mới
                Map uploadResult = cloudinary.uploader().upload(newImageFile.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                existing.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh báo cáo thất bại: " + e.getMessage());
            }
        }

        trafficReportRepo.update(existing);
        return AdminTrafficReportMapper.toDTO(existing);
    }

    @Override
    public List<TrafficReport> getVerifiedReports() {
        return this.trafficReportRepo.getVerifiedReports();
    }

}
