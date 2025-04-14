package com.quinhat.mapper;

import com.quinhat.dto.TrafficReportDTO;
import com.quinhat.dto.UserDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import java.util.Set;

public class TrafficReportMapper {

    public static TrafficReportDTO toDTO(TrafficReport report) {
        if (report == null) {
            return null;
        }

        User user = report.getUserId();

        // Truyền tất cả các field bạn muốn từ User
        Set<String> fields = Set.of("fullName", "email", "phone");

        UserDTO userDTO = user != null ? new UserDTO(user, fields) : null;

        return new TrafficReportDTO(
            report.getId(),
            report.getTitle(),
            report.getAddress(),
            report.getLatitude(),
            report.getLongitude(),
            report.getImage(),
            report.getDescription(),
            report.getCreatedAt(),
            report.getIsVerified(),
            userDTO
        );
    }
}
