/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.pojo.User;
import com.quinhat.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@ControllerAdvice
@RequestMapping("/admin/dashboard")
public class IndexController {

    @Autowired
    UserService userService;

    @ModelAttribute
    public void commonResponse(Model model) {
//        model.addAttribute("categories", this.cateService.getCates());
    }

    @GetMapping({"", "/"})
    public String index(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("products", this.prodService.getProducts(params));
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
   
}
