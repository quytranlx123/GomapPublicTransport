/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.Resquest.TrafficReportRequest;
import com.quinhat.dto.TrafficReportDTO;
import com.quinhat.dto.UserDTO;
import com.quinhat.mapper.TrafficReportMapper;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.services.TrafficReportService;
import com.quinhat.services.UserService;
import com.quinhat.utils.JwtUtils;
import jakarta.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    private UserService UserService;

    @Autowired
    private UserService userDetailsService;

//    @GetMapping("/traffic-reports")
//    public ResponseEntity<List<TrafficReportDTO>> getTrafficReports(@RequestParam Map<String, String> params) {
//        List<TrafficReport> reports = trafficReportService.getTrafficReports(params);
//        List<TrafficReportDTO> dtos = reports.stream()
//                .map(TrafficReportMapper::toDTO)
//                .toList();
//
//        return ResponseEntity.ok(dtos);
//    }
    @GetMapping("/traffic-reports")
    public ResponseEntity<ApiResponse<List<TrafficReportDTO>>> getTrafficReports(Principal principal) {
        String username = principal.getName();
        User userInfo = this.userDetailsService.getUserByUsername(username);
        int userId = userInfo.getId();

        Map<String, String> params = Map.of("userId", String.valueOf(userId));

        List<TrafficReport> reports = trafficReportService.getTrafficReports(params);
        List<TrafficReportDTO> dtos = reports.stream()
                .map(TrafficReportMapper::toDTO)
                .toList();

        ApiResponse<List<TrafficReportDTO>> response = new ApiResponse<>(dtos, HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/traffic-reports",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrafficReport> create(
            @RequestParam Map<String, String> params,
            @RequestParam("image") MultipartFile image,
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

}
