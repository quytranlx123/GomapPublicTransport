/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.TrafficReportDTO;
import com.quinhat.dto.UserDTO;
import com.quinhat.pojo.User;
import com.quinhat.services.UserService;
import com.quinhat.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService userDetailsService;

    // api tạo user
    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestParam Map<String, String> params, @RequestParam(value = "avatar") MultipartFile avatar) {
        return new ResponseEntity<>(this.userDetailsService.addUser(params, avatar), HttpStatus.CREATED);
    }

    // api login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {

        if (this.userDetailsService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getUsername());
                User userInfo = this.userDetailsService.getUserByUsername(u.getUsername());

                Set<String> fields = Set.of("username", "fullName", "email", "phone", "birthday", "avatar", "gender");
                UserDTO userDto = new UserDTO(userInfo, fields);

                Map<String, Object> result = new HashMap<>();
                result.put("token", token);
                result.put("username", userDto.getUsername());
                result.put("fullName", userDto.getFullName());
                result.put("email", userDto.getEmail());
                result.put("phone", userDto.getPhone());
                result.put("birthday", userDto.getBirthday());
                result.put("avatar", userDto.getAvatar());
                result.put("gender", userDto.getGender());

                AbandonedConnectionCleanupThread.checkedShutdown();

                return ResponseEntity.ok().body(result);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userDetailsService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    // api cập nhật user
    @PatchMapping(path = "/users/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @RequestParam Map<String, String> params,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            Principal principal) {
        try {
            String username = principal.getName();
            User updated = this.userDetailsService.updateUser(username, params, avatar);
            Set<String> fields = Set.of("username", "fullName", "email", "phone", "birthday", "avatar", "gender");
            UserDTO userDto = new UserDTO(updated, fields);

            ApiResponse<UserDTO> response = new ApiResponse<>(userDto, HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cập nhật thất bại");
        }
    }

    // api thay đổi mật khẩu
    @PutMapping("/users/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body,
            Principal principal) {
        String username = principal.getName();
        String oldPass = body.get("oldPassword");
        String newPass = body.get("newPassword");

        boolean success = this.userDetailsService.changePassword(username, oldPass, newPass);
        if (success) {
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu cũ không đúng");
        }
    }

}
