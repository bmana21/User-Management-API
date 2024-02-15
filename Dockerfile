FROM openjdk:17
LABEL authors="beso"

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by your Spring Boot application
COPY target/User-Management-API-0.0.1-SNAPSHOT.jar app.jar



# Expose the port that your application listens on (replace 8080 with your actual port)
EXPOSE 8080

# Set the command to run your application when the container starts
CMD ["java", "-jar", "app.jar"]