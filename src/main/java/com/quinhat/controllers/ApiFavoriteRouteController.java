/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quinhat.controllers;

import com.quinhat.dto.ApiResponse;
import com.quinhat.dto.FavoriteRouteDTO;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.services.FavoriteRouteService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/api/favorite-routes")
public class ApiFavoriteRouteController {

    @Autowired
    private FavoriteRouteService favoriteRouteService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<FavoriteRouteDTO>>> getFavoriteRoutesByUserId(@PathVariable int userId) {
        List<FavoriteRoute> favoriteRoutes = this.favoriteRouteService.getFavoriteRoutesByUserId(userId);

        List<FavoriteRouteDTO> favoriteRouteDTOs = favoriteRoutes.stream().map(f -> new FavoriteRouteDTO(
                f.getId(),
                f.getUserId().getId(),
                f.getRouteId().getId(),
                f.getCreatedAt()
        )).collect(Collectors.toList());

        ApiResponse<List<FavoriteRouteDTO>> response = new ApiResponse<>(favoriteRouteDTOs, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<String>> createFavoriteRoute(@RequestBody Map<String, Object> body) {
        Integer userId = (Integer) body.get("userId");
        Integer routeId = (Integer) body.get("routeId");

        try {
            favoriteRouteService.create(userId, routeId);

            ApiResponse<String> response = new ApiResponse<>(null, HttpStatus.CREATED.value(), "Thành công");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            // Trả về phản hồi lỗi với thông báo ngoại lệ
            ApiResponse<String> response = new ApiResponse<>(null, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFavoriteRoute(@PathVariable Integer id) {
        favoriteRouteService.delete(id);
        ApiResponse<String> response = new ApiResponse<>(null, HttpStatus.OK.value(), "Xóa thành công");
        return ResponseEntity.ok(response);
    }
}
