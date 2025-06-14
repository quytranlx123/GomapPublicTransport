/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminUserDTO;
import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.dto.Resquest.UserNotificationRequest;
import com.quinhat.services.UserNotificationService;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/user-notifications")
public class UserNotificationController {

    @Autowired
    private UserNotificationService userNotificationService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getUserNotificationsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminUserNotificationDTO> notifications = userNotificationService.getUserNotificationsPaginated(page, size);
        long total = userNotificationService.countUserNotifications();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("userNotifications", notifications);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

//    @PostMapping("/save")
//    public String saveUserNotifications(@ModelAttribute("adminUserNotificationForm") AdminDashboardForm form) {
//        for (AdminUserNotificationDTO dto : form.getAdminUserNotificationDTOs()) {
//            // Gọi service để lưu từng mục
//            userNotificationService.save(dto);
//        }
//
//        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
//    }
    @PostMapping("/save")
    public ResponseEntity<String> saveUserNotifications(@RequestBody UserNotificationRequest request) {
        if (request.getNotificationId() == null || request.getUserIds() == null || request.getUserIds().isEmpty()) {
            return ResponseEntity.badRequest().body("Thiếu notificationId hoặc userIds.");
        }

        for (Integer userId : request.getUserIds()) {
            AdminUserNotificationDTO dto = new AdminUserNotificationDTO();
            dto.setNotificationId(request.getNotificationId());
            dto.setUserId(userId);

            userNotificationService.save(dto);
        }

        return ResponseEntity.ok("Gửi thông báo thành công.");
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {

        userNotificationService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminUserNotificationDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminUserNotificationDTO formData
    ) {
        formData.setId(id);
        AdminUserNotificationDTO updated = userNotificationService.update(formData);
        return ResponseEntity.ok(updated);
    }

}
