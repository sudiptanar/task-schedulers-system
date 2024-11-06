package com.sn.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Task implements Comparable<Task>, Runnable {
    private String id;
    private String description;
    private String taskDefinitionId;
    private Priority priority;
    private Instant scheduledTime;
    private Instant start;
    private Instant end;
    private Duration duration;
    private boolean isRecurring;
    private Status status;
    private long recurrenceInterval; // in milliseconds

    @Override
    public int compareTo(Task other) {
        // Prioritize by highest priority, then earliest scheduled time
        if (this.priority != other.priority) {
            return Integer.compare(other.priority.getPriority(), this.priority.getPriority());
        }
        return this.scheduledTime.compareTo(other.scheduledTime);
    }

    public enum Status {
        SCHEDULED, RUNNING, FAILED, FINISHED
    }

    public enum Priority {
        BACKGROUND(1),
        VERY_LOW(2),
        LOW(3),
        NORMAL(4),
        MEDIUM(5),
        HIGH(6),
        VERY_HIGH(7),
        CRITICAL(8);

        private final int intPriority;

        Priority(int priority) {
            this.intPriority = priority;
        }

        public int getPriority() {
            return intPriority;
        }

        public static Priority getByInt(int intPriority) {
            for (Priority priority : Priority.values()) {
                if (priority.intPriority == intPriority) {
                    return priority;
                }
            }
            throw new IllegalArgumentException("No enum constant with priority " + intPriority);
        }
    }
}

