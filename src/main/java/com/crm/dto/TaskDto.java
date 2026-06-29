package com.crm.dto;

import com.crm.enums.TaskPriority;
import com.crm.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskDto {
    private Long taskId;
    private Long leadId;
    private Long assignedTo;
    private String assignedUserName;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate dueDate;
}
