package com.quinhat.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinhat.dto.AdminFavoriteRouteDTO;
import com.quinhat.dto.AdminNotificationDTO;
import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.dto.AdminScheduleDTO;
import com.quinhat.dto.AdminStationDTO;
import com.quinhat.dto.AdminTrafficReportDTO;
import com.quinhat.dto.AdminUserDTO;
import com.quinhat.dto.AdminUserNotificationDTO;
import com.quinhat.dto.AdminVehicleDTO;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.services.FavoriteRouteService;
import com.quinhat.services.NotificationService;
import com.quinhat.services.RouteService;
import com.quinhat.services.RouteStationService;
import com.quinhat.services.ScheduleService;
import com.quinhat.services.StationService;
import com.quinhat.services.TrafficReportService;
import com.quinhat.services.UserNotificationService;
import com.quinhat.services.UserService;
import com.quinhat.services.VehicleService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ControllerAdvice
@RequestMapping("/admin/dashboard")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private FavoriteRouteService favoriteRouteService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RouteStationService routeStationService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TrafficReportService trafficReportService;

    @Autowired
    private UserNotificationService userNotificationService;

    @ModelAttribute
    public void commonResponse(Model model) {
        // Có thể thêm dữ liệu dùng chung cho tất cả view ở đây
    }

    @GetMapping({"", "/"})
    public String index(
            Model model,
            @RequestParam(defaultValue = "0") int userPage,
            @RequestParam(defaultValue = "10") int userSize,
            @RequestParam Map<String, String> params
    ) throws JsonProcessingException {
        List<AdminRouteDTO> routes = routeService.getAllRoutes();
        List<AdminVehicleDTO> vehicles = vehicleService.getAllVehicles();
        List<AdminStationDTO> stations = stationService.getAllStations();
        List<AdminTrafficReportDTO> trafficReports = trafficReportService.getAllTrafficReports();


        model.addAttribute("routes", routes);
        model.addAttribute("stations", stations);
        model.addAttribute("trafficReports", trafficReports);
        model.addAttribute("vehicles", vehicles);

        long totalUsers = userService.countUsers();
        List<AdminUserDTO> users = userService.getUsersPaginated(userPage, userSize);
        int totalPages = (int) Math.ceil((double) totalUsers / userSize);
        model.addAttribute("users", users);
        model.addAttribute("userCurrentPage", userPage);
        model.addAttribute("userTotalPages", totalPages);

        return "index";
    }
//    @GetMapping({"", "/"})
//    public String index(Model model, @RequestParam Map<String, String> params) throws JsonProcessingException {
//        List<AdminUserDTO> users = userService.getAllUsers();
//        List<AdminVehicleDTO> vehicles = vehicleService.getAllVehicles();
//        List<AdminFavoriteRouteDTO> favoriteRoutes = favoriteRouteService.getAllFavoriteRoutes();
//        List<AdminRouteDTO> routes = routeService.getAllRoutes();
//        List<AdminNotificationDTO> notifications = notificationService.getAllNotifications();
//        List<AdminRouteStationDTO> routeStations = routeStationService.getAllRouteStations();
//        List<AdminScheduleDTO> schedules = scheduleService.getAllSchedules();
//        List<AdminStationDTO> stations = stationService.getAllStations();
//        List<AdminTrafficReportDTO> trafficReports = trafficReportService.getAllTrafficReports();
//        List<AdminUserNotificationDTO> userNotifications = userNotificationService.getAllUserNotifications();
//        List<Object[]> top5FavoriteRoutes = favoriteRouteService.getTop5FavoriteRoutes();
//        List<Map<String, Object>> result = new ArrayList<>();
//
//        model.addAttribute("users", users);
//        model.addAttribute("vehicles", vehicles);
//        model.addAttribute("favoriteRoutes", favoriteRoutes);
//        model.addAttribute("routes", routes);
//        model.addAttribute("notifications", notifications);
//        model.addAttribute("routeStations", routeStations);
//        model.addAttribute("schedules", schedules);
//        model.addAttribute("stations", stations);
//        model.addAttribute("trafficReports", trafficReports);
//        model.addAttribute("userNotifications", userNotifications);
//
//        for (Object[] row : top5FavoriteRoutes) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("routeName", row[0]);
//            map.put("count", row[1]);
//            result.add(map);
//        }
//
//        model.addAttribute("top5FavoriteRoutesStatsJson", new ObjectMapper().writeValueAsString(result));
//
//        // Thống kê phương tiện
//        long activeVehicles = vehicleService.countByIsActive(true);
//        long inactiveVehicles = vehicleService.countByIsActive(false);
//
//        Map<String, Long> vehicleStatusStats = new LinkedHashMap<>();
//        vehicleStatusStats.put("Đang hoạt động", activeVehicles);
//        vehicleStatusStats.put("Dừng hoạt động", inactiveVehicles);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String vehicleStatusStatsJson = objectMapper.writeValueAsString(vehicleStatusStats);
//        model.addAttribute("vehicleStatusStatsJson", vehicleStatusStatsJson);
//
//        // Thống kê phản ánh giao thông
//        int selectedMonth;
//        try {
//            selectedMonth = Integer.parseInt(params.getOrDefault("month", String.valueOf(LocalDate.now().getMonthValue())));
//        } catch (NumberFormatException e) {
//            selectedMonth = LocalDate.now().getMonthValue();
//        }
//
//        Map<String, Long> trafficReportStats = trafficReportService.countTrafficReportsByMonth(selectedMonth);
//        String trafficReportStatsJson = objectMapper.writeValueAsString(trafficReportStats);
//        model.addAttribute("trafficReportStatsJson", trafficReportStatsJson);
//        model.addAttribute("selectedMonth", selectedMonth);
//
//        return "index";
//    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
