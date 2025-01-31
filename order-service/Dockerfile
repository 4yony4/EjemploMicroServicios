# Build Stage
FROM gradle:8.6-jdk17 AS build
WORKDIR /app

# Copy build files and source code
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application
RUN gradle clean build --no-daemon

# DEBUG: Show contents of /app/build/libs/ after build
RUN ls -l /app/build/libs/

# Runtime Stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# DEBUG: Show contents before copying JAR
RUN ls -l /app/

# Copy only the JAR file
COPY --from=build /app/build/libs/app.jar /app/app.jar

# DEBUG: Show contents of final container
RUN ls -l /app/

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]