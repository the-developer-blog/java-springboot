package com.app.todoassignment.service;

import com.app.todoassignment.config.TaskNotFoundException;
import com.app.todoassignment.entity.Task;
import com.app.todoassignment.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task addTask(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> getAllTasks(String status) {
		if (status == null || status.equals("ALL")) {
			return taskRepository.findAll();
		} else {
			return taskRepository.findByStatus(Task.TaskStatus.valueOf(status));
		}
	}

	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}

	public Task updateTaskStatus(Long id, Task.TaskStatus status) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
		task.setStatus(status);
		return taskRepository.save(task);

	}

	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

}
