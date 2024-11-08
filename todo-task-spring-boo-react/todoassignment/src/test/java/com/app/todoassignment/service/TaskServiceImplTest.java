package com.app.todoassignment.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.todoassignment.config.TaskNotFoundException;
import com.app.todoassignment.entity.Task;
import com.app.todoassignment.repository.TaskRepository;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Sample Task");

        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.addTask(task);
        assertNotNull(createdTask);
        assertEquals(1L, createdTask.getId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetAllTasksWithoutStatus() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setDescription("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setDescription("Task 2");

        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> allTasks = taskService.getAllTasks(null);
        assertEquals(2, allTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTasksWithStatus() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Sample Task");
        task.setStatus(Task.TaskStatus.COMPLETED);

        when(taskRepository.findByStatus(Task.TaskStatus.COMPLETED)).thenReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks("COMPLETED");
        assertEquals(1, tasks.size());
        assertEquals(Task.TaskStatus.COMPLETED, tasks.get(0).getStatus());
        verify(taskRepository, times(1)).findByStatus(Task.TaskStatus.COMPLETED);
    }

    @Test
    void testGetTaskByIdWhenTaskExists() {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Sample Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals(1L, foundTask.get().getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskByIdWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertFalse(foundTask.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTaskStatusWhenTaskExists() {
        Task task = new Task();
        task.setId(1L);
        task.setStatus(Task.TaskStatus.PENDING);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.updateTaskStatus(1L, Task.TaskStatus.COMPLETED);
        assertEquals(Task.TaskStatus.COMPLETED, updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTaskStatusWhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> {
            taskService.updateTaskStatus(1L, Task.TaskStatus.COMPLETED);
        });

        assertEquals("Task with id 1 not found", exception.getMessage());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;
        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }
}
