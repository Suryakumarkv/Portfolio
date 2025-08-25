# --- Build Stage ---
# Use a Maven image with JDK 21 to build the application
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project and create the executable JAR
# The -DskipTests flag is added to speed up the build process
RUN mvn clean package -DskipTests


# --- Run Stage ---
# Use a lightweight JDK 21 image for the final container
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the 'builder' stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]