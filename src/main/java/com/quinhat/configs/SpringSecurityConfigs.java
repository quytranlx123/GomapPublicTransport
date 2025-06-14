/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.quinhat.utils.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.quinhat.controllers",
    "com.quinhat.repositories",
    "com.quinhat.services"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.setAllowedOrigins(List.of("http://localhost:5173")); // Thay bằng URL frontend của bạn
            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            corsConfig.setAllowedHeaders(List.of("*"));
            corsConfig.setExposedHeaders(List.of("Authorization"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                //                .requestMatchers(
                //                        "/gomap/images/**", // Cho phép truy cập ảnh không cần đăng nhập
                //                        "/css/**",
                //                        "/js/**",
                //                        "/webjars/**"
                //                ).permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/users/change-password", "/api/traffic-reports/**",
                        "/api/user-notifications/**", "/api/favorite-routes/**").authenticated()
                .requestMatchers("/api/routes/**").permitAll()
                .requestMatchers("/api/stations/**").permitAll()
                .requestMatchers("/api/route-stations/**").permitAll()
                .requestMatchers("/api/vehicles/**").permitAll()
                .requestMatchers("/api/schedules/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                .loginPage("/admin/dashboard/login")
                .loginProcessingUrl("/admin/dashboard/login")
                .defaultSuccessUrl("/admin/dashboard", true)
                .failureUrl("/admin/dashboard/login?error=true")
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/admin/dashboard/logout")
                .logoutSuccessUrl("/admin/dashboard/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                ).sessionManagement(session -> session
                .invalidSessionUrl("/admin/dashboard/login?session=expired"));
        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dxxwcby8l",
                        "api_key", "448651448423589",
                        "api_secret", "ftGud0r1TTqp0CGp5tjwNmkAm-A",
                        "secure", true));
        return cloudinary;
    }
}
