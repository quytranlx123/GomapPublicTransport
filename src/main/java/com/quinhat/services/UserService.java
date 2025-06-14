/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.services;

import com.quinhat.dto.AdminUserDTO;
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
    void save(AdminUserDTO dto);

    List<AdminUserDTO> getAllUsers();

    void delete(List<Integer> ids);

    List<AdminUserDTO> getUsersPaginated(int page, int size);

    long countUsers();

    User findById(int id);

    AdminUserDTO update(AdminUserDTO dto, MultipartFile newAvatarFile);

    //Qui
    User addUser(Map<String, String> params, MultipartFile avatar);

    User getUserByUsername(String username);

    boolean authenticate(String username, String password);

    User getUserByEmail(String email);

    void updatePassword(User user, String newPassword);

    boolean resetPasswordAndSendEmail(String email);

    User updateUser(String username, Map<String, String> params, MultipartFile avatar);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
