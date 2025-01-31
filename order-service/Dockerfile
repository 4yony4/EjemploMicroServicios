# Use a multi-stage build to reduce image size
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

# Find the JAR and rename it to app.jar
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

# Expose the correct port for each service
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]