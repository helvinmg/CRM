package com.crm.web;

import com.crm.enums.TaskStatus;
import com.crm.repository.TaskRepository;
import com.crm.service.DashboardService;
import com.crm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class WebDashboardController {

    private final DashboardService dashboardService;
    private final TaskRepository taskRepository;
    private final ReportService reportService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("summary", dashboardService.getDashboardSummary());
        model.addAttribute("overdueTasksList", taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), TaskStatus.COMPLETED));
        model.addAttribute("pipelineData", reportService.getSalesPipelineReport());
        model.addAttribute("pageTitle", "Dashboard");
        return "dashboard/index";
    }
}
