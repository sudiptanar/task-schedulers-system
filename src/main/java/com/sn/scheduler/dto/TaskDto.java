package com.sn.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;
    private String description;
    private int priority;
    private long scheduledTime;
    private long startTime;
    private long endTime;
    private long timeTaken;
    private boolean isRecurring;
    private long recurrenceInterval;
    private String status;
}
