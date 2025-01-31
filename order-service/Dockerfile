# Step 1: Use a Gradle image to build the application
FROM gradle:8.6-jdk17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy Gradle build files (to leverage Gradle caching)
COPY build.gradle settings.gradle gradle.properties ./
COPY src ./src

# Run Gradle build inside the container
RUN gradle clean build --no-daemon

# Step 2: Use a lightweight OpenJDK image for the final container
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app_service

# Copy the built JAR file from the first stage
COPY --from=build /app/build/libs/app.jar app.jar

# Expose the application port
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]