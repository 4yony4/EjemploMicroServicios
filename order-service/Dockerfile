# Use official Gradle image to build the JAR
FROM gradle:8.6-jdk17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy Gradle build files and dependencies to leverage caching
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application JAR
RUN gradle clean build --no-daemon

# Use a minimal Java runtime image for the final container
FROM openjdk:17-jdk-slim

# Set working directory in the final image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port (modify based on service)
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]