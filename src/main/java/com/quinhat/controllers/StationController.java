/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.services.RouteService;
import com.quinhat.pojo.Station;
import com.quinhat.services.StationService;

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
@RequestMapping("/admin/dashboard/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping("/save")
    public String addStation(@ModelAttribute @Valid Station station, BindingResult result, Model model) {
        station.setCreatedAt(new Date());
        stationService.save(station);

        return "redirect:/admin/dashboard";
    }
}
