# Pastebin Service

## Overview
The Pastebin Service is a robust, scalable microservice-based application designed for storing and retrieving text snippets (pastes). The system leverages a variety of modern technologies and best practices to ensure high performance, reliability, and maintainability.

## Features
- **Microservice Architecture**: Composed of independently deployable services for improved scalability and maintainability.
- **MinIO Integration**: Utilizes MinIO for efficient and scalable object storage.
- **Hash Generation Service**: Custom service for generating unique identifiers for each paste.
- **Redis Caching**: Implements Redis for high-speed data caching and improved response times.
- **PostgreSQL Database**: Robust relational database for persistent data storage.
- **Docker Containerization**: Ensures consistent environments across development, testing, and production.
- **API Gateway**: Centralizes routing and CORS configuration for improved security and management.
- **Prometheus Metrics**: Comprehensive system monitoring with custom and built-in metrics.
- **CORS Configuration**: Configured to allow cross-origin requests for improved client-side integration.
- **Spring Cloud OpenFeign**: Simplifies HTTP API clients for inter-service communication.
- **Centralized Configuration**: Uses a centralized configuration system for easy management across environments.

## Services

### API Gateway
- Routes incoming requests to appropriate services.
- Handles CORS configuration.

### API Service
- Handles incoming requests and orchestrates interactions between other services.
- **Endpoints**:
  - `POST /api/v1/paste` - Create a new paste.
  - `GET /api/v1/paste/{hash}` - Retrieve a paste by its hash.
  - `GET /api/v1/paste/search` - Search for pastes with various filters.

### Hash Generation Service
- Generates unique hashes for each paste.
- **Endpoint**:
  - `POST /generate-hash` - Generates a unique hash for the given data.

### Metrics Service
- Collects and exposes custom metrics for monitoring system performance.
- **Endpoints**:
  - `POST /api/v1/metrics/paste-created` - Increments paste creation counter.
  - `POST /api/v1/metrics/paste-viewed` - Increments paste view counter.
  - `GET /api/v1/metrics/current-values` - Retrieves current metric values.
  - `GET /api/v1/metrics/prometheus` - Exposes Prometheus-formatted metrics.

### Comment Service
- Manages comments for pastes.
- **Endpoints**:
  - `POST /api/v1/comments/{pasteHash}` - Add a comment to a paste.
  - `GET /api/v1/comments/paste/{pasteHash}` - Get comments for a specific paste.

## Technology Stack
- Java 17
- Spring Boot
- Redis
- MinIO
- PostgreSQL
- Docker
- Prometheus

## Configuration

The project uses a centralized configuration system:

- `config/default.yaml`: Contains default configurations for all services.
- `config/development.yaml`, `config/production.yaml`, `config/test.yaml`: Environment-specific configurations.
- `scripts/generate-config.sh`: Script to generate final configuration files.

### Docker
- Multi-stage Dockerfile for optimized builds
- Docker Compose for local development and testing

## Metrics and Monitoring
- Custom metrics for paste creation and viewing
- JVM and system metrics exposed via Prometheus

## Getting Started

### Prerequisites
- Java 17
- Maven
- Docker and Docker Compose

### Build and Run
Use the `deploy.sh` script to build and run the project:

```sh
./scripts/deploy.sh development
```

This script will generate the appropriate configuration, build the Docker images, and start the services using Docker Compose.

#### Deployment for Different Environments
Use the same `deploy.sh` script with different environment arguments:

```sh
./scripts/deploy.sh production
```
