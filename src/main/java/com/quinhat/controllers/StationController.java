/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminStationDTO;
import com.quinhat.services.RouteService;
import com.quinhat.pojo.Station;
import com.quinhat.services.StationService;

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

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getStationsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminStationDTO> stations = stationService.getStationsPaginated(page, size);
        long total = stationService.countStations();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("stations", stations);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveStations(@ModelAttribute("adminStationForm") AdminDashboardForm form) {
        for (AdminStationDTO dto : form.getAdminStationDTOs()) {
            // Gọi service để lưu từng mục
            stationService.save(dto);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        stationService.delete(ids);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminStationDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminStationDTO formData
    ) {
        formData.setId(id);
        AdminStationDTO updated = stationService.update(formData);
        return ResponseEntity.ok(updated);
    }
}
