import React, { useState } from 'react';
import { createTask } from '../api/taskApi';
import './AddTask.css'; // Import the CSS file for styling

interface AddTaskProps {
  onTaskAdded: () => void;
}

const AddTask: React.FC<AddTaskProps> = ({ onTaskAdded }) => {
  const [title, setTitle] = useState('');

  const handleAddTask = async () => {
    if (title.trim() === '') {
      alert('Task title cannot be empty');
      return;
    }
    await createTask({ title, status: 'PENDING' });
    setTitle('');
    onTaskAdded();
  };

  return (
    <div className="add-task-container">
      <input 
        type="text" 
        className="task-input"
        value={title} 
        onChange={(e) => setTitle(e.target.value)} 
        placeholder="Enter new task" 
      />
      <button className="task-add-btn" onClick={handleAddTask}>Add Task</button>
    </div>
  );
};

export default AddTask;
