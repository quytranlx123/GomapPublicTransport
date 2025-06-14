/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.services.NotificationService;
import com.quinhat.pojo.Notification;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getNotificationsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminNotificationDTO> notifications = notificationService.getNotificationsPaginated(page, size);
        long total = notificationService.countNotifications();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("notifications", notifications);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveNotifications(@ModelAttribute("adminNotificationForm") AdminDashboardForm form) {
        for (AdminNotificationDTO dto : form.getAdminNotificationDTOs()) {
            // Gọi service để lưu từng mục
            notificationService.save(dto);
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        notificationService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminNotificationDTO> updateUser(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminNotificationDTO formData
    ) {
        formData.setId(id);
        AdminNotificationDTO updated = notificationService.update(formData);
        return ResponseEntity.ok(updated);
    }

}
