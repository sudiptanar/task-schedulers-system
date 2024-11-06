package com.sn.scheduler.kafka.producer;

import com.sn.scheduler.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskProducer {

    private final KafkaTemplate<String, Task> kafkaTemplate;
    private final String topic;

    public TaskProducer(KafkaTemplate<String, Task> kafkaTemplate, @Value("${kafka.topic.tasks}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendTask(Task task) {
        kafkaTemplate.send(topic, task);
        System.out.println("Sent task to Kafka: " + task.getDescription());
    }
}

