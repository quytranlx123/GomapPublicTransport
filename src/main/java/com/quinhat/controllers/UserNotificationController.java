/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.services.UserNotificationService;
import com.quinhat.pojo.UserNotification;

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
@RequestMapping("/admin/dashboard/user-notifications")
public class UserNotificationController {
    @Autowired
    private UserNotificationService userNotificationService;

    @PostMapping("/save")
    public String addUserNotification(@ModelAttribute @Valid UserNotification un, BindingResult result, Model model) {
        un.setSendAt(new Date());
        userNotificationService.save(un);
        return "redirect:/admin/dashboard";
    }
}
