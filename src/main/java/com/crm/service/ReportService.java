package com.crm.service;

import com.crm.entity.Lead;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.enums.LeadStatus;
import com.crm.enums.TaskStatus;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Map<LeadStatus, Long> getSalesPipelineReport() {
        List<Lead> allLeads = leadRepository.findAll();
        return allLeads.stream().collect(Collectors.groupingBy(Lead::getStatus, Collectors.counting()));
    }

    public Map<TaskStatus, Long> getTaskActivityReport() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream().collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }

    public List<Map<String, Object>> getTeamPerformanceReport() {
        List<User> users = userRepository.findAll();
        List<Lead> leads = leadRepository.findAll();
        List<Task> tasks = taskRepository.findAll();

        return users.stream().map(user -> {
            long userLeads = leads.stream().filter(l -> l.getAssignedTo() != null && l.getAssignedTo().getUserId().equals(user.getUserId())).count();
            long userTasks = tasks.stream().filter(t -> t.getAssignedTo() != null && t.getAssignedTo().getUserId().equals(user.getUserId())).count();
            Map<String, Object> map = new HashMap<>();
            map.put("userName", user.getFullName());
            map.put("leadsAssigned", userLeads);
            map.put("tasksAssigned", userTasks);
            return map;
        }).collect(Collectors.toList());
    }
}
