package com.sn.scheduler.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;

@Document(collection = "task-occurrences")
@Data
@Builder
public class TaskOccurrence {
    @Id
    private String id;
    private String description;
    private String taskDefinitionId;
    private String priority;
    private Instant scheduledTime;
    private Instant start;
    private Instant end;
    private Duration duration;
    private boolean isRecurring;
    private long recurrenceInterval;
    private String status;
}
