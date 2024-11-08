import axios from 'axios';
import AxiosMockAdapter from 'axios-mock-adapter';
import { fetchTasks, createTask, updateTaskStatus, deleteTask } from '../api/taskApi';
import { Task } from '../types/task';

const API_URL = 'http://localhost:8081/api/v1/tasks';
let mock: AxiosMockAdapter;

beforeEach(() => {
  // Create a new mock instance before each test
  mock = new AxiosMockAdapter(axios);
});

afterEach(() => {
  // Reset the mock after each test
  mock.reset();
});

describe('taskApi functions', () => {
  test('fetchTasks should return tasks with correct response', async () => {
    const mockTasks: Task[] = [
      { id: 1, title: 'Task 1', status: 'PENDING' },
      { id: 2, title: 'Task 2', status: 'COMPLETED' },
    ];

    // Mock GET request
    mock.onGet(API_URL).reply(200, mockTasks);

    const tasks = await fetchTasks();
    expect(tasks).toEqual(mockTasks);
  });

  test('fetchTasks should handle error when the request fails', async () => {
    mock.onGet(API_URL).reply(500);

    await expect(fetchTasks()).rejects.toThrow('An unexpected error occurred');
  });

  test('createTask should create a task with correct response', async () => {
    const newTask: { title: string; status: 'PENDING' } = { title: 'New Task', status: 'PENDING' };
    const createdTask: Task = { id: 1, title: 'New Task', status: 'PENDING' };

    // Mock POST request
    mock.onPost(API_URL).reply(201, createdTask);

    const task = await createTask(newTask);
    expect(task).toEqual(createdTask);
  });

  test('createTask should handle error when the request fails', async () => {
    const newTask: { title: string; status: 'PENDING' } = { title: 'New Task', status: 'PENDING' };
    mock.onPost(API_URL).reply(400);

    await expect(createTask(newTask)).rejects.toThrow('An unexpected error occurred');
  });

  test('updateTaskStatus should update task status correctly', async () => {
    const updatedTask: Task = { id: 1, title: 'Task 1', status: 'COMPLETED' };

    // Mock PUT request
    mock.onPut(`${API_URL}/1`).reply(200, updatedTask);

    const task = await updateTaskStatus(1, 'COMPLETED');
    expect(task).toEqual(updatedTask);
  });

  test('updateTaskStatus should handle error when the request fails', async () => {
    mock.onPut(`${API_URL}/1`).reply(500);

    await expect(updateTaskStatus(1, 'COMPLETED')).rejects.toThrow('An unexpected error occurred');
  });

  test('deleteTask should delete task correctly', async () => {
    mock.onDelete(`${API_URL}/1`).reply(200);

    await expect(deleteTask(1)).resolves.toBeUndefined();
  });

  test('deleteTask should handle error when the request fails', async () => {
    mock.onDelete(`${API_URL}/1`).reply(404);

    await expect(deleteTask(1)).rejects.toThrow('An unexpected error occurred');
  });
});
