package com.quinhat.controllers;

import com.quinhat.services.DashboardService;
import java.util.HashMap;
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
    private DashboardService dashboardService;

    @ModelAttribute
    public void commonResponse(Model model) {
        // Có thể thêm dữ liệu dùng chung cho tất cả view ở đây
    }

    @GetMapping({"", "/"})
    public String index(Model model, @RequestParam Map<String, String> params) {
        Map<String, Object> data = dashboardService.getAllData();
        model.addAttribute(data);

        return "index";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
}
