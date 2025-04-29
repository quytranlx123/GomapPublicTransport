/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.services.FavoriteRouteService;
import com.quinhat.pojo.FavoriteRoute;

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
@RequestMapping("/admin/dashboard/favorite-routes")
public class FavoriteRouteController {

    @Autowired
    private FavoriteRouteService favoriteRouteService;

    @PostMapping("/save")
    public String addFavoriteRoute(@ModelAttribute @Valid FavoriteRoute fr, BindingResult result, Model model) {
        fr.setCreatedAt(new Date());
        favoriteRouteService.save(fr);

        return "redirect:/admin/dashboard";
    }
}
