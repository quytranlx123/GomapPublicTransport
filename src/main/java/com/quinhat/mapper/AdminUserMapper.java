package com.quinhat.mapper;

import com.quinhat.dto.AdminUserDTO;
import com.quinhat.pojo.User;

public class AdminUserMapper {

    public static AdminUserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setAvatar(user.getAvatar());
        dto.setUserRole(user.getUserRole());
        dto.setBirthday(user.getBirthday());
        dto.setGender(user.getGender());
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public static User toEntity(AdminUserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAvatar(dto.getAvatar());
        user.setUserRole(dto.getUserRole());
        user.setBirthday(dto.getBirthday());
        user.setGender(dto.getGender());
        user.setIsActive(dto.getIsActive());
        user.setCreatedAt(dto.getCreatedAt());
        return user;
    }
}
