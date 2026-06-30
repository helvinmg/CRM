package com.crm.controller;

import com.crm.repository.TaskRepository;
import com.crm.service.DashboardService;
import com.crm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequiredArgsConstructor
public class DashboardController {

    // Spring will automatically inject these dependencies because of @RequiredArgsConstructor
    private final DashboardService dashboardService;
    private final TaskRepository taskRepository;
    private final ReportService reportService;

    /**
     * Handles GET requests to the "/dashboard" URL.
     * 
     * @param model The Model object is used to pass data from the backend to the Thymeleaf template.
     * @return The name of the Thymeleaf template to render (in this case, "dashboard/index.html").
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Fetch the high-level summary metrics (total customers, active leads, etc.)
        model.addAttribute("summary", dashboardService.getDashboardSummary());
        
        // Fetch tasks that are past their due date and haven't been completed yet
        model.addAttribute("overdueTasksList", taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), "COMPLETED"));
        
        // Fetch data for the sales pipeline visualization
        model.addAttribute("pipelineData", reportService.getSalesPipelineReport());
        
        // Set the title of the page, which the layout template will use
        model.addAttribute("pageTitle", "Dashboard");
        
        // Tells Spring to look for a file at src/main/resources/templates/dashboard/index.html
        return "dashboard/index";
    }
}
