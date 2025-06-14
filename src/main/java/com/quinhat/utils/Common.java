/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.utils;

import org.springframework.stereotype.Component;

/**
 *
 * @author tranngocqui
 */
public class Common {

    public static String extractPublicIdFromUrl(String url) {
        if (url == null || url.isBlank()) {
            return null;
        }
        try {
            String[] parts = url.split("/");
            String filenameWithExt = parts[parts.length - 1];
            return filenameWithExt.substring(0, filenameWithExt.lastIndexOf('.'));
        } catch (Exception e) {
            return null;
        }
    }

    public static String toLowerCase(String input) {
        return input.toLowerCase();
    }

    public static boolean isPlainPassword(String password) {
        return password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$");
    }

}
