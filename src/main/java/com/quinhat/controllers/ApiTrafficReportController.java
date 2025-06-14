/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.TrafficReportDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.services.TrafficReportService;
import com.quinhat.services.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api")
public class ApiTrafficReportController {

    @Autowired
    private TrafficReportService trafficReportService;

    @Autowired
    private UserService userDetailsService;

    @GetMapping("/traffic-reports/all")
    public ResponseEntity<ApiResponse<List<AdminTrafficReportDTO>>> getAllTrafficReports() {

        List<AdminTrafficReportDTO> trafficReports = trafficReportService.getAllTrafficReports();

        ApiResponse<List<AdminTrafficReportDTO>> response = new ApiResponse<>(
                trafficReports,
                HttpStatus.OK.value(),
                "Lấy danh sách tất cả các phản ánh thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/traffic-reports")
    public ResponseEntity<ApiResponse<List<TrafficReportDTO>>> getTrafficReports(
            Principal principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String type) {
        String username = principal.getName();
        User userInfo = this.userDetailsService.getUserByUsername(username);
        int userId = userInfo.getId();

        Map<String, String> params = new HashMap<>();
        params.put("userId", String.valueOf(userId));

        if (type != null && !type.isEmpty()) {
            params.put("type", type);
        }

        List<TrafficReport> reports = trafficReportService.getTrafficReports(params, page, pageSize);
        long total = this.trafficReportService.countTrafficReportsByUserId(userId, type);
        Integer totalAsInteger = (int) total;

        List<TrafficReportDTO> dtos = reports.stream()
                .map(TrafficReportDTO::fromEntity)
                .toList();

        ApiResponse<List<TrafficReportDTO>> response = new ApiResponse<>(dtos, HttpStatus.OK.value(), "Lấy danh sách các phản ánh thành công", page, pageSize, totalAsInteger);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/traffic-reports",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrafficReport> create(
            @RequestParam Map<String, String> params,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Principal principal) {

        String username = principal.getName();
        User user = this.userDetailsService.getUserByUsername(username);

        TrafficReport createdReport = this.trafficReportService.createTrafficReport(params, image, user);

        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    @PutMapping(path = "/traffic-reports/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrafficReport> updateTrafficReport(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        TrafficReport updated = this.trafficReportService.updateTrafficReport(id, params, image);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/traffic-reports/{id}")
    public ResponseEntity<?> deleteTrafficReport(@PathVariable("id") int id) {
        TrafficReport report = trafficReportService.getTrafficReportById(id);

        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy phản ánh giao thông.");
        }

        trafficReportService.deleteTrafficReport(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/traffic-reports/verified")
    public ResponseEntity<ApiResponse<List<TrafficReportDTO>>> getVerifiedReports() {
        List<TrafficReport> reports = trafficReportService.getVerifiedReports();

        List<TrafficReportDTO> dtos = reports.stream()
                .map(TrafficReportDTO::fromEntity)
                .toList();

        ApiResponse<List<TrafficReportDTO>> response = new ApiResponse<>(
                dtos,
                HttpStatus.OK.value(),
                "Lấy danh sách phản ánh đã xác thực thành công"
        );

        return ResponseEntity.ok(response);
    }

}
