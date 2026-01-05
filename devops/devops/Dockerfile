# Stage 1: Build the application using Maven with Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application using Java 21 JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from the 'build' stage
# This wildcard ensures it finds your JAR regardless of the project name
COPY --from=build /app/target/*.jar app.jar

# Standard port for Spring Boot
EXPOSE 8080

# Run the application with optimized memory settings for cloud environments
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]