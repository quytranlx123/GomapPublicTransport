/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.repositories;

import com.quinhat.pojo.User;

/**
 *
 * @author admin
 */
public interface UserRepository {
    User getUserByUsername(String username);
    User addUser(User u);
}
