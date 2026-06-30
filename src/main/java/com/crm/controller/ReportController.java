package com.crm.controller;

import com.crm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Only users with the ADMIN role can access any methods in this controller
public class ReportController {

    private final ReportService reportService;

    /**
     * Handles GET requests to "/reports".
     * Shows the main reports dashboard.
     * 
     * @param model Used to pass data to the view
     * @return The "reports/index.html" template
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Reports");
        return "reports/index";
    }

    /**
     * Handles GET requests to "/reports/sales".
     * Displays a pipeline showing leads grouped by their status.
     * 
     * @param model Used to pass data to the view
     * @return The "reports/sales.html" template
     */
    @GetMapping("/sales")
    public String salesReport(Model model) {
        model.addAttribute("reportData", reportService.getSalesPipelineReport());
        model.addAttribute("pageTitle", "Sales Pipeline Report");
        return "reports/sales";
    }

    /**
     * Handles GET requests to "/reports/tasks".
     * Displays tasks grouped by their current status.
     * 
     * @param model Used to pass data to the view
     * @return The "reports/tasks.html" template
     */
    @GetMapping("/tasks")
    public String taskReport(Model model) {
        model.addAttribute("reportData", reportService.getTaskActivityReport());
        model.addAttribute("pageTitle", "Task Activity Report");
        return "reports/tasks";
    }

    /**
     * Handles GET requests to "/reports/team".
     * Displays performance metrics for the team.
     * 
     * @param model Used to pass data to the view
     * @return The "reports/team.html" template
     */
    @GetMapping("/team")
    public String teamReport(Model model) {
        model.addAttribute("reportData", reportService.getTeamPerformanceReport());
        model.addAttribute("pageTitle", "Team Performance Report");
        return "reports/team";
    }
}
