# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy the jar file from target folder
COPY target/*.jar app.jar

# Run the application on the port provided by Render
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
