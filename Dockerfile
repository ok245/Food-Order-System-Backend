# Use Maven image to build the project
FROM maven:3.8.6-openjdk-17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven POM file and source code
COPY pom.xml .
COPY src ./src

# Run Maven to build the project
RUN mvn clean package -DskipTests

# Use a smaller base image for the runtime
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/Online-Food-Ordering-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
