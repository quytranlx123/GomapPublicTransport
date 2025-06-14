/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.services.ScheduleService;
import com.quinhat.pojo.Schedule;

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
@RequestMapping("/admin/dashboard/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getSchedulesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminScheduleDTO> schedules = scheduleService.getSchedulesPaginated(page, size);
        long total = scheduleService.countSchedules();
        int totalPages = (int) Math.ceil((double) total / size);

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", schedules);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveSchedules(@ModelAttribute("adminScheduleForm") AdminDashboardForm form) {
        for (AdminScheduleDTO dto : form.getAdminScheduleDTOs()) {
            // Gọi service để lưu từng mục
            scheduleService.save(dto);
        }

        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        scheduleService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminScheduleDTO> updateUserNotification(
            @RequestParam("id") Integer id,
            @ModelAttribute AdminScheduleDTO formData
    ) {
        formData.setId(id);
        AdminScheduleDTO updated = scheduleService.update(formData);
        return ResponseEntity.ok(updated);
    }

}
