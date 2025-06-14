/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.services.RouteService;
import com.quinhat.pojo.Route;
import com.quinhat.services.RouteStationService;

import jakarta.validation.Valid;
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

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/route-stations")
public class RouteStationController {

    @Autowired
    private RouteStationService routeStationService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getRouteStationsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminRouteStationDTO> routeStations = routeStationService.getRouteStationsPaginated(page, size);
        long total = routeStationService.countRouteStations();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("routeStations", routeStations);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveRouteStations(@ModelAttribute("adminRouteStationForm") AdminDashboardForm form) {
        for (AdminRouteStationDTO dto : form.getAdminRouteStationDTOs()) {
            // Gọi service để lưu từng mục
            routeStationService.save(dto);
        }

        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        routeStationService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminRouteStationDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminRouteStationDTO formData
    ) {
        formData.setId(id);
        AdminRouteStationDTO updated = routeStationService.update(formData);
        return ResponseEntity.ok(updated);
    }

}
