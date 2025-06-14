package com.quinhat.mapper;

import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.TrafficReport.ReportType;
import com.quinhat.pojo.User;

public class AdminTrafficReportMapper {

    public static AdminTrafficReportDTO toDTO(TrafficReport entity) {
        if (entity == null) {
            return null;
        }

        AdminTrafficReportDTO dto = new AdminTrafficReportDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAddress(entity.getAddress());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setVerified(entity.getIsVerified());
        dto.setUserId(entity.getUserId() != null ? entity.getUserId().getId() : null);
        dto.setType(entity.getType() != null ? entity.getType().name() : null); // thêm dòng này

        return dto;
    }

    public static TrafficReport toEntity(AdminTrafficReportDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        TrafficReport entity = new TrafficReport();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAddress(dto.getAddress());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setImage(dto.getImage());
        entity.setDescription(dto.getDescription());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setIsVerified(dto.isVerified());
        entity.setUserId(user);
        if (dto.getType() != null) {
            try {
                entity.setType(ReportType.valueOf(dto.getType())); // parse enum
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid NotificationType: " + dto.getType());
            }
        }

        return entity;
    }
}
