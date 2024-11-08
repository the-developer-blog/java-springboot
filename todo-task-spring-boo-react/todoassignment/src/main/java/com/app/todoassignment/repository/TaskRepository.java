package com.app.todoassignment.repository;

import com.app.todoassignment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Task.TaskStatus status);
}
