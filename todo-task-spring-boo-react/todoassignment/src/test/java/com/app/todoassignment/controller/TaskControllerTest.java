package com.app.todoassignment.controller;



import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.todoassignment.controller.TaskController;
import com.app.todoassignment.entity.Task;
import com.app.todoassignment.entity.Task.TaskStatus;
import com.app.todoassignment.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.PENDING);
    }

    @Test
    void shouldAddTask() throws Exception {
        when(taskService.addTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Task")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.status", is("PENDING")));

        verify(taskService, times(1)).addTask(any(Task.class));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.getAllTasks(null)).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Task")))
                .andExpect(jsonPath("$[0].description", is("Test Description")))
                .andExpect(jsonPath("$[0].status", is("PENDING")));

        verify(taskService, times(1)).getAllTasks(null);
    }

    @Test
    void shouldGetTasksByStatus() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.getAllTasks("PENDING")).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/tasks")
                .param("status", "PENDING")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("PENDING")));

        verify(taskService, times(1)).getAllTasks("PENDING");
    }

    @Test
    void shouldUpdateTaskStatus() throws Exception {
        task.setStatus(TaskStatus.COMPLETED);
        when(taskService.updateTaskStatus(1L, TaskStatus.COMPLETED)).thenReturn(task);

        mockMvc.perform(put("/api/v1/tasks/1")
                .param("status", "COMPLETED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("COMPLETED")));

        verify(taskService, times(1)).updateTaskStatus(1L, TaskStatus.COMPLETED);
    }

    @Test
    void shouldDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }
}
