# Build stage
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/hash-generator-1.0-SNAPSHOT.jar .
EXPOSE 8081
ENTRYPOINT ["java","-jar","hash-generator-1.0-SNAPSHOT.jar"]
