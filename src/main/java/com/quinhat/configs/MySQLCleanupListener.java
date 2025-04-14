/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.configs;

import jakarta.servlet.annotation.WebListener;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 *
 * @author ASUS
 */
@WebListener
public class MySQLCleanupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Không cần xử lý khi khởi động
    }
}
