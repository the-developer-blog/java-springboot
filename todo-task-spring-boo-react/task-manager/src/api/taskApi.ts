// src/api/taskApi.ts
import axios from 'axios';
import { Task } from '../types/task';

const API_URL = 'http://localhost:8081/api/v1/tasks';

// Helper function to extract error message
const handleApiError = (error: any): string => {
  if (axios.isAxiosError(error)) {
    // If the error response exists, return its message
    if (error.response) {
      return `Error ${error.response.status}: ${error.response.data.message || 'An error occurred'}`;
    }
    // If no response, this means there was a network error
    return 'Network error: Unable to reach the server';
  }
  // Any other unexpected errors
  return 'An unexpected error occurred';
};

export const fetchTasks = async (status?: string): Promise<Task[]> => {
  try {
    const response = await axios.get(API_URL, { params: { status } });
    return response.data;
  } catch (error) {
    console.error(handleApiError(error));
    throw new Error(handleApiError(error));
  }
};

export const createTask = async (task: { title: string; status: 'PENDING' }): Promise<Task> => {
  try {
    const response = await axios.post(API_URL, task);
    return response.data;
  } catch (error) {
    console.error(handleApiError(error));
    throw new Error(handleApiError(error));
  }
};

export const updateTaskStatus = async (id: number, status: 'COMPLETED' | 'PENDING'): Promise<Task> => {
  try {
    const response = await axios.put(`${API_URL}/${id}`, null, { params: { status } });
    return response.data;
  } catch (error) {
    console.error(handleApiError(error));
    throw new Error(handleApiError(error));
  }
};

export const deleteTask = async (id: number): Promise<void> => {
  try {
    await axios.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error(handleApiError(error));
    throw new Error(handleApiError(error));
  }
};
