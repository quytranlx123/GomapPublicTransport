/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminVehicleDTO;

import com.quinhat.services.VehicleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("admin/dashboard/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getVehiclesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminVehicleDTO> vehicles = vehicleService.getVehiclesPaginated(page, size);
        long total = vehicleService.countVehicles();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("vehicles", vehicles);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveVehicles(@ModelAttribute("adminVehicleForm") AdminDashboardForm form) {
        for (AdminVehicleDTO dto : form.getAdminVehicleDTOs()) {
            // Gọi service để lưu từng mục
            vehicleService.save(dto);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        vehicleService.delete(ids);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminVehicleDTO> updateUser(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminVehicleDTO formData
    ) {
        formData.setId(id);
        AdminVehicleDTO updated = vehicleService.update(formData);
        return ResponseEntity.ok(updated);
    }
}
