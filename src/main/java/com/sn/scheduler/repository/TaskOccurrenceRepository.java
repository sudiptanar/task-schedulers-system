package com.sn.scheduler.repository;

import com.sn.scheduler.model.TaskOccurrence;
import com.sn.scheduler.repository.custom.CustomTaskOccurrenceRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskOccurrenceRepository extends MongoRepository<TaskOccurrence, String>, CustomTaskOccurrenceRepository {
    // Additional query methods if needed
}
