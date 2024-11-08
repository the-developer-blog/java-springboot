
---

### README for Java Spring Boot Backend

```markdown
# To-Do List Application - Backend

This is the backend part of the To-Do List application developed with Java and Spring Boot. It provides RESTful APIs for managing tasks, including adding, updating, deleting, and fetching tasks.

## Features

- RESTful API endpoints for task management.
- JWT-based authentication for secure API access.
- In-memory database (H2) for data persistence.
- Multithreading support for concurrent task handling.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- JUnit for unit testing

## Getting Started

### Prerequisites

Make sure you have the following installed:

- JDK (>= 11)
- Maven (for dependency management)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/todo-backend.git
   cd todo-backend
   
   
2. Build the project:

mvn clean install


Running the Application
To start the Spring Boot application, run:

mvn spring-boot:run



This will start the application on http://localhost:8080.

API Endpoints
POST /api/v1/tasks - Add a new task.
GET /api/v1/tasks - Get all tasks (optional filter by status).
PUT /api/tasks/{id}?status=INPROGRESS Update a task’s status.
DELETE /api/v1/tasks/{id} - Delete a task.


Running Tests
To run the unit tests, execute:

mvn test



