package com.quinhat.controllers;

import com.quinhat.dto.AdminRouteFavoriteCountDTO;
import com.quinhat.dto.Response.AdminStatisticsResponseDTO;
import com.quinhat.services.StatisticsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/dashboard/statistics")
public class StatisticalController {

    @Autowired
    private StatisticsService statisticsService;
//
//    @GetMapping("/all")
//    @ResponseBody
//    public Map<String, Object> getAllStatistics() {
//        List<AdminRouteFavoriteCountDTO> top5Routes = statisticsService.getTop5FavoriteRoutes();
//        // Nếu cần thêm các thống kê khác, gọi service tương ứng và put vào map
//        // e.g. List<OtherStatDTO> otherStats = statisticsService.getOtherStats();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("top5Routes", top5Routes);
//        // response.put("otherStats", otherStats);
//
//        return response;
//    }

    @GetMapping("/all")
    @ResponseBody
    public Map<String, Object> getAllStatistics() {
        Map<String, Object> data = new HashMap<>();

        data.put("top5FavoriteRoutes", List.of(
                Map.of("label", "Tuyến 01", "value", 120),
                Map.of("label", "Tuyến 02", "value", 95),
                Map.of("label", "Tuyến 03", "value", 80),
                Map.of("label", "Tuyến 04", "value", 65),
                Map.of("label", "Tuyến 05", "value", 50)
        ));

        data.put("vehicleStatus", Map.of(
                "Hoạt động", 150,
                "Bảo trì", 30,
                "Không hoạt động", 20
        ));

        data.put("trafficReports", Map.of(
                "Tắc đường", 70,
                "Tai nạn", 20,
                "Khác", 10
        ));

        // Bạn có thể thêm các loại thống kê khác nếu muốn
        return data;
    }
}
