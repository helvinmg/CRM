package com.crm.service;

import com.crm.dto.TaskDto;
import java.util.List;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface TaskService {
    List<TaskDto> getAllTasks();
    List<TaskDto> getTasksByUser(Long userId);
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
    TaskDto markTaskComplete(Long id);
    TaskDto updateTaskStatus(Long id, String status);
}
