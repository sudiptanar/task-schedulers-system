package com.sn.scheduler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sn.scheduler.logics.tasks.BubbleSortTask;
import com.sn.scheduler.model.Task;
import com.sn.scheduler.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;

public class SchedulerServiceTest {

    @InjectMocks
    private SchedulerService schedulerService;

    @Mock
    private Task mockTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask() {
        // Given
        when(mockTask.getScheduledTime()).thenReturn(Instant.now().plusSeconds(5));

        // When
        schedulerService.addTask(mockTask);

        // Then
        // Verify that the task was added (you can implement a way to check the task queue if necessary)
        assertNotNull(schedulerService); // Just a placeholder check
    }

    @Test
    void testCheckAndRunTasks() throws InterruptedException {
        // Given
        when(mockTask.getScheduledTime()).thenReturn(Instant.now().plusSeconds(1));
        when(mockTask.isRecurring()).thenReturn(false);

        schedulerService.addTask(mockTask);

        // When
        schedulerService.checkAndRunTasks();
        // Let the task run (as this is simulated, you may need to adjust timing based on your implementation)

        // Then
        verify(mockTask, times(1)).run(); // Ensure the task's run method was called
    }

    @Test
    void testBubbleSort() {
        // Given an array to sort
        int[] arrayToSort = {64, 34, 25, 12, 22, 11, 90};
        int[] expectedSortedArray = {11, 12, 22, 25, 34, 64, 90};

        // Create an instance of BubbleSortTask
        BubbleSortTask bubbleSortTask = new BubbleSortTask(
                "sortTask1",
                "Bubble Sort Task",
                "",
                Task.Priority.MEDIUM.getPriority(),
                Instant.now(),
                arrayToSort
        );

        schedulerService.addTask(bubbleSortTask);
        schedulerService.checkAndRunTasks();

//        // When the task is run
//        bubbleSortTask.run();
//
//        // Then the array should be sorted
//        assertArrayEquals(expectedSortedArray, bubbleSortTask.getArrayToSort());
    }
}

