# Pastebin Service

## Overview

The Pastebin Service is a robust, scalable microservice-based application designed for storing and retrieving text snippets (pastes). The system leverages a variety of modern technologies and best practices to ensure high performance, reliability, and maintainability.

## Features

- **Microservice Architecture**: Composed of independently deployable services for improved scalability and maintainability.
- **MinIO Integration**: Utilizes MinIO for efficient and scalable object storage.
- **Hash Generation Service**: Custom service for generating unique identifiers for each paste.
- **Redis Caching**: Implements Redis for high-speed data caching and improved response times.
- **PostgreSQL Database**: Robust relational database for persistent data storage.
- **Nginx Load Balancer**: Efficiently distributes incoming traffic across multiple service instances.
- **Docker Containerization**: Ensures consistent environments across development, testing, and production.
- **Prometheus Metrics**: Comprehensive system monitoring with custom and built-in metrics.
- **CORS Configuration**: Configured to allow cross-origin requests for improved client-side integration.
- **Spring Cloud OpenFeign**: Simplifies HTTP API clients for inter-service communication.

## Services

### API Service
- Handles incoming requests and orchestrates interactions between other services.
- **Endpoints**:
  - `POST /paste` - Create a new paste.
  - `GET /paste/{hash}` - Retrieve a paste by its hash.

### Hash Generation Service
- Generates unique hashes for each paste.
- **Endpoint**:
  - `POST /generate-hash` - Generates a unique hash for the given data.

### Metrics Service
- Collects and exposes custom metrics for monitoring system performance.
- **Endpoints**:
  - `POST /metrics/paste-created` - Increments paste creation counter.
  - `POST /metrics/paste-viewed` - Increments paste view counter.
  - `GET /metrics/current-values` - Retrieves current metric values.
  - `GET /actuator/prometheus` - Exposes Prometheus-formatted metrics.

## Technology Stack

- Java 17
- Spring Boot
- Redis
- MinIO
- PostgreSQL
- Nginx
- Docker
- Prometheus

## Configuration

### Application Properties
- Datasource configuration for PostgreSQL
- Redis configuration for caching
- MinIO configuration (URL, access key, secret key, bucket name)
- Metrics and monitoring settings

### Docker
- Multi-stage Dockerfile for optimized builds
- Docker Compose for local development and testing

### Kubernetes
- Deployment manifests for each service
- Service and Ingress configurations

## Metrics and Monitoring

- Custom metrics for paste creation and viewing
- JVM and system metrics exposed via Prometheus
- Grafana dashboards for visualizing metrics (TODO)

## Getting Started

### Prerequisites
- Java 17
- Maven
- Docker and Docker Compose

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
