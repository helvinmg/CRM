package com.crm.service;

import com.crm.dto.TaskDto;
import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();
    List<TaskDto> getTasksByUser(Long userId);
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
    TaskDto markTaskComplete(Long id);
}
