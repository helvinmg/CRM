package com.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    // Simple mocks for the reports

    @GetMapping("/sales-pipeline")
    public ResponseEntity<Map<String, Object>> getSalesPipeline() {
        Map<String, Object> report = new HashMap<>();
        report.put("message", "Sales Pipeline Report - Lead count by status");
        return ResponseEntity.ok(report);
    }

    @GetMapping("/task-activity")
    public ResponseEntity<Map<String, Object>> getTaskActivity() {
        Map<String, Object> report = new HashMap<>();
        report.put("message", "Task Activity Report - Tasks by status/user");
        return ResponseEntity.ok(report);
    }

    @GetMapping("/team-performance")
    public ResponseEntity<Map<String, Object>> getTeamPerformance() {
        Map<String, Object> report = new HashMap<>();
        report.put("message", "Team Performance Report - Leads/tasks per user");
        return ResponseEntity.ok(report);
    }
}
