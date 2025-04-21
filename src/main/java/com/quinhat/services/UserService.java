/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {
    //Qui

    void save(User user);

    void deleteUser(int id);

    List<User> getAllUsers();
    
    //Qui

    User getUserByUsername(String username);

    User addUser(Map<String, String> params, MultipartFile avatar);

    boolean authenticate(String username, String password);

    User updateUser(String username, Map<String, String> params, MultipartFile avatar);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
