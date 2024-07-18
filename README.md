# Pastebin Service

## Overview

The Pastebin Service is a fully microservice-based application designed to store and retrieve text snippets (pastes). The system leverages MinIO for storage, a custom hash generation service for unique paste identifiers, and Spring Boot for the microservice architecture.

## Features

- **Microservice Architecture**: The application is composed of distinct, independently deployable services.
- **MinIO Integration**: Utilizes MinIO for object storage.
- **Hash Generation Service**: Generates unique hashes for each paste.
- **CORS Configuration**: Configured to allow cross-origin requests.
- **Spring Cloud OpenFeign**: Simplifies HTTP API clients.

## Services

### Paste Service

- **Endpoints**:
  - `POST /paste` - Create a new paste.
  - `GET /paste/{hash}` - Retrieve a paste by its hash.

### Hash Generation Service

- **Endpoint**:
  - `POST /generate-hash` - Generates a unique hash for the given data.

## Configuration

### Application Properties

- **Datasource**: Configured for PostgreSQL.
- **Hash Generator**: URL for the hash generator service.
- **MinIO**: URL, access key, secret key, and bucket name for MinIO.

### Docker

- Multi-stage Dockerfile for building and running the application.

## Getting Started

### Prerequisites

- Java 17
- Maven
- Docker

### Build and Run

1. **Build the Application**:
   ```sh
   mvn clean package -DskipTests

2. **Run with Docker**:
   ```sh
   docker-compose up --build

## Testing

- Unit and integration tests are provided to ensure functionality.
- Run tests with:
  ```sh
  mvn test


