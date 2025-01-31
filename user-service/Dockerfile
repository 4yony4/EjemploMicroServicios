# Use official Gradle image to build the JAR
FROM gradle:8.6-jdk17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy Gradle build files first (to leverage Gradle's cache)
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application JAR
RUN gradle clean build --no-daemon

# Use a minimal Java runtime image for the final container
FROM openjdk:17-jdk-slim

# Set working directory in the final image
WORKDIR /app

# Copy only the main JAR file (adjust the name accordingly)
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Expose the port (modify based on service)
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]