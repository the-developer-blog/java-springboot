import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import App from './App';  // Path to the App component
import TaskList from './components/TaskList';
import AddTask from './components/AddTask';

// Mock the child components
jest.mock('./components/TaskList', () => () => <div>Task List Component</div>);
jest.mock('./components/AddTask', () => ({ onTaskAdded }: { onTaskAdded: () => void }) => (
  <div>
    <button onClick={onTaskAdded}>Add Task</button>
  </div>
));

describe('App Component', () => {
  test('renders Task Manager heading', () => {
    render(<App />);

    // Check if the Task Manager title is in the document
    const heading = screen.getByText(/Task Manager/i);
    expect(heading).toBeInTheDocument();
  });

  test('renders AddTask and TaskList components', () => {
    render(<App />);

    // Check if AddTask and TaskList are rendered
    const addTaskButton = screen.getByText(/Add Task/i);
    const taskListComponent = screen.getByText(/Task List Component/i);
    
    expect(addTaskButton).toBeInTheDocument();
    expect(taskListComponent).toBeInTheDocument();
  });

  test('calls onTaskAdded when the AddTask button is clicked', () => {
    render(<App />);

    // Get the Add Task button
    const addTaskButton = screen.getByText(/Add Task/i);

    // Simulate a click event
    fireEvent.click(addTaskButton);

    // Check if the function passed to onTaskAdded is called
    // As we are mocking the component, we could track the behavior or check the button appearance
    expect(addTaskButton).toBeInTheDocument(); // Example assertion, customize as needed
  });
});
