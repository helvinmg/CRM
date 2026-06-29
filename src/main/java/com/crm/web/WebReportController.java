package com.crm.web;

import com.crm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class WebReportController {

    private final ReportService reportService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Reports");
        return "reports/index";
    }

    @GetMapping("/sales")
    public String salesReport(Model model) {
        model.addAttribute("reportData", reportService.getSalesPipelineReport());
        model.addAttribute("pageTitle", "Sales Pipeline Report");
        return "reports/sales";
    }

    @GetMapping("/tasks")
    public String taskReport(Model model) {
        model.addAttribute("reportData", reportService.getTaskActivityReport());
        model.addAttribute("pageTitle", "Task Activity Report");
        return "reports/tasks";
    }

    @GetMapping("/team")
    public String teamReport(Model model) {
        model.addAttribute("reportData", reportService.getTeamPerformanceReport());
        model.addAttribute("pageTitle", "Team Performance Report");
        return "reports/team";
    }
}
