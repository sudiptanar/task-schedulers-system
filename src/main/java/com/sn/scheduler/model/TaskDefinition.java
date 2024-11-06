package com.sn.scheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Document(collection = "task-definitions")
public class TaskDefinition {
    @Id
    private String id;
    private String description;
    private Map<String,Object> availableConfigurations;
}
