package com.sn.scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.scheduler.controller.TaskController;
import com.sn.scheduler.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testScheduleBubbleSortTask() throws Exception {
//        // Given
//        RunnableTaskRequest request = new RunnableTaskRequest();
//        request.setTaskType("bubbleSort");
//        request.setTaskParams("[64, 34, 25, 12, 22, 11, 90]");
//        request.setPriority(1);
//        request.setDelay(5);
//
//        when(schedulerService.addTask(any())).thenReturn(true); // Simulating a successful task addition
//
//        // When & Then
//        mockMvc.perform(post("/tasks/run")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(request)))
//                .andExpect(status().isOk());
    }

    @Test
    void testInvalidTaskType() throws Exception {
//        // Given
//        RunnableTaskRequest request = new RunnableTaskRequest();
//        request.setTaskType("invalidTaskType"); // Invalid task type
//        request.setTaskParams("[64, 34, 25, 12, 22, 11, 90]");
//        request.setPriority(1);
//        request.setDelay(5);
//
//        // When & Then
//        mockMvc.perform(post("/tasks/run")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(request)))
//                .andExpect(status().isBadRequest()); // Assuming you return a 400 for invalid task types
    }
}

