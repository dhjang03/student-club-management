# Student Club Management

## Introduction
This project is a Student Club and Event Management System, designed to facilitate the management of student clubs and their events at the University of Melbourne. It enables students to interact with their clubs, create and manage events, RSVP to events, and manage funding applications for their clubs. The project is re-implementation of the original project using Spring Boot.

## Background
Managing student clubs and events can be challenging without a centralized system. This application addresses this issue by providing a web-based platform where students and administrators can efficiently manage their club-related activities. Key functionalities include creating, amending, and canceling events, as well as handling RSVPs and funding applications.

The backend is implemented in Java using Spring Boot, while the frontend leverages a modern JavaScript framework. The system is built to handle essential student club operations and ensures data integrity and security.

## Tech Stack

### Backend
- **Java 23**
  - Spring Boot 3.4.1
    - Spring Security
    - Spring Data JPA
    - Spring Web
  - Lombok for boilerplate reduction
  - JWT (jjwt 0.12.6) for authentication

### Frontend
- **Next.js 15.1.4**
  - React 19
  - TypeScript 5
  - TailwindCSS 3.4.1
  - Axios 1.7.9 for API requests
  - JWT Decode 4.0.0
  - React Toastify 11.0.3 for notifications

### Database
- PostgreSQL (version managed by Spring Boot 3.4.1)

### Development Tools
- ESLint 9
- PostCSS 8
- Spring Boot DevTools

### Infrastructure
- Docker & Docker Compose

## Documentations

- [PLACEHOLDER]

## Getting Started

### Prerequisites
- Docker and Docker Compose installed on your system

### Environment Setup

1. Create a `.env` file in the root directory of the project
2. Copy the following environment variables and adjust them according to your setup:

```env
# JWT Configuration
JWT_SECRET=f97e0211f5639eb97d55a439adc981b4b48584e9cff069501fdb1dbaa334af5ddef03464716648b7273d86d0b0970a315e1b0975a154466be04343bedacf3135
JWT_EXPIRATION=3600000

# Database Configuration 
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/student-club-management-db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

### Running the Application
1. Build and start the containers:
```bash
docker compose up --build
```
2. Frontend will be available at http://localhost:3000
3. Following users are available in the system:

| Username | Password | Email                        | Role    | Comment            |
|----------|----------|------------------------------|---------|--------------------|
| john     | password | john@example.com             | Student | admin of all clubs |
| sam      | password | sam@example.com              | Staff   |                    |
| jane     | password | jane@example.com             | Student |                    |
| peter    | password | peter.parker@example.com     | Student |                    |
| mary     | password | mary.jane@example.com        | Student |                    |
| tony     | password | tony.stark@example.com       | Student |                    |
| bruce    | password | bruce.wayne@example.com      | Student |                    |
| clark    | password | clark.kent@example.com       | Student |                    |
| steve    | password | steve.rogers@example.com     | Student |                    |
| natasha  | password | natasha.romanoff@example.com | Student |                    |
| wanda    | password | wanda.maximoff@example.com   | Student |                    |
| vision   | password | vision@example.com           | Student |                    |
| scott    | password | scott.lang@example.com       | Student |                    |
| tchalla  | password | tchalla@example.com          | Student |                    |
