package com.sn.scheduler.repository.custom.impl;

import com.sn.scheduler.model.Task;
import com.sn.scheduler.model.TaskDefinition;
import com.sn.scheduler.model.TaskOccurrence;
import com.sn.scheduler.repository.custom.CustomTaskOccurrenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;

@Repository
public class CustomTaskDefinitionRepositoryImpl implements CustomTaskOccurrenceRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomTaskDefinitionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void upsertTaskDefinition(Task task) {
        TaskOccurrence taskOccurrence = TaskOccurrence.builder()
                .id(task.getId())
                .description(task.getDescription())
                .taskDefinitionId(task.getTaskDefinitionId())
                .priority(task.getPriority().name())
                .scheduledTime(task.getScheduledTime())
                .start(task.getStart())
                .end(task.getEnd())
                .duration(task.getDuration())
                .isRecurring(task.isRecurring())
                .recurrenceInterval(task.getRecurrenceInterval())
                .status(task.getStatus().name())
                .build();


        Query query = new Query(Criteria.where("_id").is(taskOccurrence.getId()));

        Update update = new Update();
        for (Field field : taskOccurrence.getClass().getDeclaredFields()) {
            field.setAccessible(true); // Make private fields accessible
            try {
                Object value = field.get(taskOccurrence); // Get the value of the field
                if (value != null) { // Only set non-null fields
                    update.set(field.getName(), value); // Use field name as the key
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mongoTemplate.findAndModify(query, update, TaskDefinition.class, mongoTemplate.getCollectionName(TaskDefinition.class));
    }
}

