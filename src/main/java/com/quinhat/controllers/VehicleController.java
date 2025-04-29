/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.pojo.Vehicle;
import com.quinhat.pojo.Route;
import com.quinhat.services.RouteService;

import com.quinhat.services.UserService;
import com.quinhat.services.VehicleService;
import jakarta.validation.Valid;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tranngocqui
 */
@Controller
@RequestMapping("admin/dashboard/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RouteService routeService;

    @PostMapping("/save")
    public String addVehicle(@ModelAttribute @Valid Vehicle vehicle,
            BindingResult result,
            Model model) {

        vehicle.setCreatedAt(new Date());
        vehicle.setUpdatedAt(new Date());
        vehicleService.save(vehicle);
        return "redirect:/admin/dashboard";
    }
}
