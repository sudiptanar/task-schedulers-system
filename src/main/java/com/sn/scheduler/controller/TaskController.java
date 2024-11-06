package com.sn.scheduler.controller;

import com.sn.scheduler.dto.TaskDto;
import com.sn.scheduler.kafka.producer.TaskProducer;
import com.sn.scheduler.logics.tasks.BubbleSortTask;
import com.sn.scheduler.model.Task;
import com.sn.scheduler.repository.TaskOccurrenceRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskProducer taskProducer;
    private final TaskOccurrenceRepository taskOccurrenceRepository;

    @Autowired
    public TaskController(TaskProducer taskProducer, TaskOccurrenceRepository taskOccurrenceRepository) {
        this.taskProducer = taskProducer;
        this.taskOccurrenceRepository = taskOccurrenceRepository;
    }

    @PostMapping
    public ResponseEntity<Document> addTask(@RequestBody TaskDto taskDto) {
        String permanentTaskId = "";

        Task task = new BubbleSortTask(taskDto.getId(), taskDto.getDescription(), permanentTaskId, taskDto.getPriority(), Instant.ofEpochMilli(taskDto.getScheduledTime()), new int[]{23, 43, 456});
        taskProducer.sendTask(task);

        taskOccurrenceRepository.upsertTaskDefinition(task);
        return ResponseEntity.ok(new Document().append("userMassage", "Task submitted successfully").append("developerMassage", "Task submitted to kafka"));
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        // Logic to retrieve and return all tasks
        return null;
    }

    // Additional endpoints for pausing, resuming, or deleting tasks
}

