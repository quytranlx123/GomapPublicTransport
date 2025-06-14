/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.dto.AdminUserDTO;
import com.quinhat.pojo.User;
import java.util.List;

/**
 *
 * @author admin
 */
public interface UserRepository {

    //Qui
    boolean deleteUser(int id);

    void delete(List<Integer> ids);

    List<AdminUserDTO> getAllUsers();

    void save(User u);

    List<AdminUserDTO> getUsersPaginated(int page, int size);

    long countUsers();

    User findById(int id);

    void update(User u);
    //Quí

    User getUserByUsername(String username);

    User addUser(User u);

    boolean authenticate(String username, String password);

    User updateUser(User u);

    User getUserByEmail(String email);

    void updatePassword(User user, String newPassword);
}
