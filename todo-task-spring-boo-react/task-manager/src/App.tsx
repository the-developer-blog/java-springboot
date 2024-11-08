import React from 'react';
import TaskList from './components/TaskList';
import AddTask from './components/AddTask';
import './App.css'; // Importing the CSS file for styling

function App() {
  return (
    <div className="app-container">
      <header className="app-header">
        <h1 className="app-title">Task Manager</h1>
      </header>
      <div className="content-container">
        <AddTask onTaskAdded={() => window.location.reload()} />
        <TaskList />
      </div>
    </div>
  );
}

export default App;
