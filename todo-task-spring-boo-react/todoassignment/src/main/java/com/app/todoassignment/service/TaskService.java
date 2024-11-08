package com.app.todoassignment.service;

import com.app.todoassignment.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    public Task addTask(Task task);
    public List<Task> getAllTasks(String status);
    public Optional<Task> getTaskById(Long id);
    public Task updateTaskStatus(Long id, Task.TaskStatus status);
    public void deleteTask(Long id);
}
