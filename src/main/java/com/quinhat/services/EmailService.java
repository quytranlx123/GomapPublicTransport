/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quinhat.services;

/**
 *
 * @author ASUS
 */
public interface EmailService {

    void sendEmail(String to, String subject, String body) throws Exception;
}
