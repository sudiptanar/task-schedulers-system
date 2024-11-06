package com.sn.scheduler.repository.custom;

import com.sn.scheduler.model.Task;
import com.sn.scheduler.model.TaskOccurrence;

public interface CustomTaskOccurrenceRepository {
    void upsertTaskDefinition(Task task);
}
