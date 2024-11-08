import React, { useEffect, useState } from 'react';
import { fetchTasks, updateTaskStatus, deleteTask } from '../api/taskApi';
import { Task } from '../types/task';
import TaskFilter from './TaskFilter';
import './TaskList.css'; // Importing the CSS file for styling

const TaskList: React.FC = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [filter, setFilter] = useState<string>('all');

  useEffect(() => {
    loadTasks();
  }, [filter]);

  const loadTasks = async () => {
    const data = await fetchTasks(filter !== 'all' ? filter : undefined);
    setTasks(data);
  };

  const handleStatusChange = async (id: number, status: 'COMPLETED' | 'PENDING') => {
    await updateTaskStatus(id, status);
    loadTasks();
  };

  const handleDelete = async (id: number) => {
    await deleteTask(id);
    loadTasks();
  };

  return (
    <div className="task-list-container">
      <h1 className="task-list-title">Task List</h1>
      <TaskFilter filter={filter} onFilterChange={setFilter} />
      <ul className="task-list">
        {tasks.map((task) => (
          <li key={task.id} className="task-item">
            <div className="task-info">
              <span>{task.title}</span>
              <span className="task-status">{task.status}</span>
            </div>
            <div className="task-actions">
              <button 
                className="task-status-btn" 
                onClick={() => handleStatusChange(task.id, task.status === 'COMPLETED' ? 'PENDING' : 'COMPLETED')}>
                Mark as {task.status === 'COMPLETED' ? 'PENDING' : 'COMPLETED'}
              </button>
              <button className="task-delete-btn" onClick={() => handleDelete(task.id)}>Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskList;
