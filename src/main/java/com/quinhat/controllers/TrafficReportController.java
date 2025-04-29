/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.services.TrafficReportService;
import com.quinhat.pojo.TrafficReport;

import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("/admin/dashboard/traffic-reports")
public class TrafficReportController {

    @Autowired
    private TrafficReportService trafficReportService;

    @PostMapping("/save")
    public String addTrafficReport(@ModelAttribute @Valid TrafficReport report, BindingResult result, Model model) {
        report.setCreatedAt(new Date());
        trafficReportService.save(report);

        return "redirect:/admin/dashboard";
    }
}
