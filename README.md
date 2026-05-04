# Mini Banking Application

This project is a simplified banking backend system built using Java and Spring Boot.
It exposes core banking operations through RESTful APIs, secured with JWT authentication.

The application is designed as a learning project to demonstrate backend development
best practices such as layered architecture, security, persistence, and testing.


# Features

- User authentication using JWT
- Account management (create, retrieve, update)
- Basic banking operations (deposit, withdraw, transfer)
- RESTful APIs with proper HTTP methods and status codes
- Relational database persistence (H2)
- Centralized exception handling
- Logging with SLF4J
- Unit and integration tests


# Technology Stack

- Java 25+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Tokens)
- H2 Database
- Maven
- JUnit & Mockito
- SLF4J / Logback


# Architecture Overview

The application follows a layered architecture:

Controller → Service → Repository → Database

- Controller Layer: Handles HTTP requests and responses
- Service Layer: Contains business logic
- Repository Layer: Manages database access using JPA
- Security Layer: Handles authentication and authorization with JWT


# Project Structure

src/main/java
- controller
- service
- repository
- model
- security
- exception

src/test/java
- unit tests
- integration tests



# Setup Instructions

# Prerequisites
- Java 17 or later
- Maven

# Running the Application

1. Clone the repository:
   git clone https://github.com/daniel-constantinmarin-glitch/BankingApp.git

2. Navigate to the project directory:
   cd "Banking App"

3. Run the application:
   mvn spring-boot:run

4. The application will start on:
   http://localhost:8080

