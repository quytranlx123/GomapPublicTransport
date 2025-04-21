/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.quinhat.pojo.User;
import java.nio.file.Path;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("admin/dashboard/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String addUser(@ModelAttribute @Valid User user,
            BindingResult result,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            Model model) {

        userService.save(user);
        return "redirect:/admin/dashboard";
    }

    // Phương thức lưu avatar
    private String saveAvatar(MultipartFile avatar) throws IOException {
        String uploadDir = "uploads/avatars/";  // Thư mục lưu avatar
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
        // Tạo tên file duy nhất để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + avatar.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.write(path, avatar.getBytes());
        return uploadDir + fileName;  // Trả về đường dẫn file đã lưu
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }
}
