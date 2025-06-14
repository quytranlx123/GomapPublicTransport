/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminRouteFavoriteCountDTO;
import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.dto.Response.AdminStatisticsResponseDTO;
import com.quinhat.services.TrafficReportService;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.services.StatisticsService;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/traffic-reports")
public class TrafficReportController {

    @Autowired
    private TrafficReportService trafficReportService;
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getTrafficReportsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminTrafficReportDTO> reports = trafficReportService.getTrafficReportsPaginated(page, size);
        long total = trafficReportService.countTrafficReports();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("trafficReports", reports);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveTrafficReports(@ModelAttribute("adminTrafficReportForm") AdminDashboardForm form) {
        for (AdminTrafficReportDTO dto : form.getAdminTrafficReportDTOs()) {
            // Gọi service để lưu từng mục
            trafficReportService.save(dto);
        }

        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        trafficReportService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminTrafficReportDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @ModelAttribute AdminTrafficReportDTO formData
    ) {

        formData.setId(id);
        AdminTrafficReportDTO updated = trafficReportService.update(formData, imageFile);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/statistics")
    @ResponseBody
    public AdminStatisticsResponseDTO getStatistics() {
        List<AdminRouteFavoriteCountDTO> top5Routes = statisticsService.getTop5FavoriteRoutes();
        return new AdminStatisticsResponseDTO(top5Routes);
    }

}
