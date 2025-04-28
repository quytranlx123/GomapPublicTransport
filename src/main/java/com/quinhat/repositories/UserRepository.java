/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.pojo.User;
import java.util.List;

/**
 *
 * @author admin
 */

public interface UserRepository {
    //Qui
    boolean deleteUser(int id);

    List<User> getAllUsers();
    
    void save(User user);
    //Quí

    User getUserByUsername(String username);

    User addUser(User u);

    boolean authenticate(String username, String password);

    User updateUser(User u);
}
