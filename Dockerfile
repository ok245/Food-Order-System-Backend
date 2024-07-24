# Use a base image with Java
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at the specified working directory
COPY target/Online-Food-Ordering-0.0.1-SNAPSHOT.jar /app/Online-Food-Ordering.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "Online-Food-Ordering.jar"]
