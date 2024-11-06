package com.sn.scheduler.repository;

import com.sn.scheduler.model.TaskDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskDefinitionRepository  extends MongoRepository<TaskDefinition, String> {
}

