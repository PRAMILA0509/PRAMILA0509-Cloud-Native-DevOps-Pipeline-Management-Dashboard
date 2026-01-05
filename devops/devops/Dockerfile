# Stage 1: Build the application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Build the JAR file, skipping tests to speed up deployment
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the built JAR from the 'build' stage
# Note: Ensure 'devops' matches your spring.application.name or artifactId
COPY --from=build /app/target/*.jar app.jar
# Render uses the PORT environment variable; Spring Boot will listen on it
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]