/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.pojo.User;
import com.quinhat.repositories.UserRepository;
import com.quinhat.services.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getUserRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();

        u.setUsername(params.get("username"));
        u.setFullName(params.get("fullName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));

        // Chuyển đổi birthday từ String sang Date
        String birthdayStr = params.get("birthday");
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthday = sdf.parse(birthdayStr);
                u.setBirthday(birthday);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        u.setCreatedAt(new Date());
        u.setIsActive(true);
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setUserRole(params.get("userRole")); // ✅ sửa lỗi hardcoded "userRole"

        u.setGender(params.get("gender")); // Nếu User có field gender

        // Upload ảnh đại diện nếu có
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return this.userRepo.addUser(u);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    // Cập nhật user
    @Override
    public User updateUser(String username, Map<String, String> params, MultipartFile avatar) {
        User u = this.getUserByUsername(username);

        if (u == null) {
            return null;
        }

        // Only update the fields if they are provided in the params
        if (params.containsKey("fullName")) {
            u.setFullName(params.get("fullName"));
        }
        if (params.containsKey("email")) {
            u.setEmail(params.get("email"));
        }
        if (params.containsKey("phone")) {
            u.setPhone(params.get("phone"));
        }
        if (params.containsKey("gender")) {
            u.setGender(params.get("gender"));
        }

        // Update birthday if provided
        if (params.containsKey("birthday") && !params.get("birthday").isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthday = sdf.parse(params.get("birthday"));
                u.setBirthday(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Upload avatar if provided
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Save the updated user
        return this.userRepo.updateUser(u);
    }

    //Thay đổi mật khẩu
    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        User u = this.getUserByUsername(username);

        if (u != null && passwordEncoder.matches(oldPassword, u.getPassword())) {
            u.setPassword(passwordEncoder.encode(newPassword));
            this.userRepo.updateUser(u);
            return true;
        }

        return false;
    }

}
