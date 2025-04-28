package com.quinhat.controllers;

import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Schedule;
import com.quinhat.pojo.Station;
import com.quinhat.pojo.TrafficReport;
import com.quinhat.pojo.User;
import com.quinhat.pojo.UserNotification;
import com.quinhat.pojo.Vehicle;
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
    public String index(Model model, @RequestParam Map<String, String> params) {
        List<User> users = userService.getAllUsers();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        List<FavoriteRoute> favoriteRoutes = favoriteRouteService.getAllFavoriteRoutes();
        List<Route> routes = routeService.getAllRoutes();
        List<Notification> notifications = notificationService.getAllNotifications();
        List<RouteStation> routeStations = routeStationService.getAllRouteStations();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<Station> stations = stationService.getAllStations();
        List<TrafficReport> trafficReports = trafficReportService.getAllTrafficReports();
        List<UserNotification> userNotifications = userNotificationService.getAllUserNotifications();

        model.addAttribute("users", users);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("favoriteRoutes", favoriteRoutes);
        model.addAttribute("routes", routes);
        model.addAttribute("notifications", notifications);
        model.addAttribute("routeStations", routeStations);
        model.addAttribute("schedules", schedules);
        model.addAttribute("stations", stations);
        model.addAttribute("trafficReports", trafficReports);
        model.addAttribute("userNotifications", userNotifications);

        return "index";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
