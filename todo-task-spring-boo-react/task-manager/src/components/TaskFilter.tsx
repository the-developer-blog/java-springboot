import React from 'react';
import './TaskFilter.css'; // Import the CSS for styling

interface TaskFilterProps {
  filter: string;
  onFilterChange: (filter: string) => void;
}

const TaskFilter: React.FC<TaskFilterProps> = ({ filter, onFilterChange }) => {
  return (
    <div className="task-filter-container">
      <label htmlFor="task-filter" className="task-filter-label">Filter:</label>
      <select 
        id="task-filter"
        className="task-filter-select"
        value={filter} 
        onChange={(e) => onFilterChange(e.target.value)}
      >
        <option value="ALL">All</option>
        <option value="COMPLETED">Completed</option>
        <option value="PENDING">Pending</option>
      </select>
    </div>
  );
};

export default TaskFilter;
