/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.services.RouteService;
import com.quinhat.pojo.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getRoutesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminRouteDTO> routes = routeService.getRoutesPaginated(page, size);
        long total = routeService.countRoutes();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("routes", routes);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveRoutes(@ModelAttribute AdminDashboardForm form) {
        for (AdminRouteDTO dto : form.getAdminRouteDTOs()) {
            // Gọi service để lưu từng mục
            routeService.save(dto);
        }

        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        routeService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminRouteDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminRouteDTO formData
    ) {
        formData.setId(id);
        AdminRouteDTO updated = routeService.update(formData);
        return ResponseEntity.ok(updated);
    }
}
