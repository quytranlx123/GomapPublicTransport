/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.services.FavoriteRouteService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/admin/dashboard/favorite-routes")
public class FavoriteRouteController {

    @Autowired
    private FavoriteRouteService favoriteRouteService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getFavoriteRoutesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminFavoriteRouteDTO> favoriteRoutes = favoriteRouteService.getFavoriteRoutesPaginated(page, size);
        long total = favoriteRouteService.countFavoriteRoutes();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("favoriteRoutes", favoriteRoutes);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveFavoriteRoutes(@ModelAttribute("adminFavoriteRouteForm") AdminDashboardForm form) {
        for (AdminFavoriteRouteDTO dto : form.getAdminFavoriteRouteDTOs()) {
            // Gọi service để lưu từng mục
            favoriteRouteService.save(dto);
        }

        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String deleteFavoriteRoutes(@RequestParam("ids") List<Integer> ids) {
        favoriteRouteService.delete(ids);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminFavoriteRouteDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminFavoriteRouteDTO formData
    ) {
        formData.setId(id);
        AdminFavoriteRouteDTO updated = favoriteRouteService.update(formData);
        return ResponseEntity.ok(updated);
    }

}
