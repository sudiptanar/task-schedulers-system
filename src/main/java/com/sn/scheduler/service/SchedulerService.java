package com.sn.scheduler.service;

import com.sn.scheduler.model.Task;
import com.sn.scheduler.repository.TaskDefinitionRepository;
import com.sn.scheduler.repository.TaskOccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SchedulerService {

    private final PriorityQueue<Task> taskQueue;
    private final ExecutorService executor;

    private final TaskDefinitionRepository taskDefinitionRepository;
    private final TaskOccurrenceRepository taskOccurrenceRepository;


    @Autowired
    public SchedulerService(@Value("${parallel.task.count}") int parallelTaskCount, TaskDefinitionRepository taskDefinitionRepository, TaskOccurrenceRepository taskOccurrenceRepository) {
        this.executor = Executors.newFixedThreadPool(parallelTaskCount);
        taskQueue = new PriorityQueue<>();

        this.taskDefinitionRepository = taskDefinitionRepository;
        this.taskOccurrenceRepository = taskOccurrenceRepository;
    }


    // Method to add a task to the priority queue
    public void addTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.offer(task);
            System.out.println("Task added to queue: " + task.getDescription());
        }
    }

    // Kafka listener method to consume tasks from the Kafka topic
    @KafkaListener(topics = "${kafka.topic.tasks}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeTask(Task task) {
        System.out.println("Received task from Kafka: " + task.getDescription());
        addTask(task);
    }

    // This method will run periodically
    @Scheduled(fixedRateString = "${recheck.task.per.millisecond}")
    public void checkAndRunTasks() {
        synchronized (taskQueue) {
            while (!taskQueue.isEmpty() && taskQueue.peek().getScheduledTime().isBefore(Instant.now())) {
                Task task = taskQueue.poll();
                if (task != null) {
                    executor.submit(() -> executeTask(task));
                }
            }
        }
    }

    private void executeTask(Task task) {
        try {
            task.setStatus(Task.Status.RUNNING);
            task.setStart(Instant.now());
            taskOccurrenceRepository.upsertTaskDefinition(task);

            System.out.println("Executing: " + task.getDescription());
            task.run();
            task.setEnd(Instant.now());
            task.setStatus(Task.Status.FINISHED);
        } catch (Exception e) {
            task.setStatus(Task.Status.FAILED);
            e.printStackTrace();
        } finally {
            task.setEnd(Instant.now());
            task.setDuration(Duration.between(task.getStart(), task.getEnd()));
            taskOccurrenceRepository.upsertTaskDefinition(task);
        }

        // If the task is recurring, reschedule it
        if (task.isRecurring()) {
            task.setScheduledTime(task.getScheduledTime().plus(task.getRecurrenceInterval(), ChronoUnit.MILLIS));
            task.setId(UUID.randomUUID().toString());
            task.setStart(null);
            task.setEnd(null);
            task.setDuration(null);
            task.setStatus(Task.Status.SCHEDULED);

            taskOccurrenceRepository.upsertTaskDefinition(task);
            addTask(task);
        }
    }
}
