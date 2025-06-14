/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.dto.AdminDashboardForm;
import com.quinhat.dto.AdminUserDTO;
import com.quinhat.pojo.User;
import com.quinhat.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("admin/dashboard/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/page")
    @ResponseBody
    public Map<String, Object> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AdminUserDTO> users = userService.getUsersPaginated(page, size);
        long totalUsers = userService.countUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / size);

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return response;
    }

    @PostMapping("/save")
    public String saveUsers(@RequestParam("avatars") List<MultipartFile> avatars,
            @ModelAttribute AdminDashboardForm form) {
        if (avatars.size() != form.getAdminUserDTOs().size()) {
            throw new IllegalStateException("Số lượng avatars và users không khớp. Vui lòng kiểm tra lại dữ liệu.");
        }
        for (int i = 0; i < form.getAdminUserDTOs().size(); i++) {
            AdminUserDTO adminUserDTO = form.getAdminUserDTOs().get(i);
            MultipartFile avatarFile = avatars.get(i);
            // Lưu file avatar
            if (!avatarFile.isEmpty()) {
                try {
                    Map uploadResult = cloudinary.uploader().upload(avatarFile.getBytes(), ObjectUtils.emptyMap());
                    String avatarUrl = (String) uploadResult.get("url");
                    adminUserDTO.setAvatar(avatarUrl);
                } catch (IOException e) {
                    // Ghi log lỗi nhưng không dừng hệ thống
                    System.err.println("Lỗi khi upload avatar lên Cloudinary: " + e.getMessage());
                    adminUserDTO.setAvatar(null); // Đảm bảo hệ thống vẫn hoạt động ngay cả khi upload thất bại
                }
            }
            userService.save(adminUserDTO);
        }
        return "redirect:/admin/dashboard"; // hoặc trang thông báo thành công
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("ids") List<Integer> ids) {
        userService.delete(ids);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<AdminUserDTO> updateUser(
            @RequestParam("id") Integer id,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            @ModelAttribute AdminUserDTO formData
    ) {
        formData.setId(id);
        AdminUserDTO updated = userService.update(formData, avatarFile);
        return ResponseEntity.ok(updated);
    }

}
