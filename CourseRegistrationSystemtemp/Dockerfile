# Use official Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies (build cache)
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the project
COPY . .

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose port (matches server.port in application.properties)
EXPOSE 9000

# Run the jar
CMD ["java", "-jar", "target/CourseRegistrationSystem-0.0.1-SNAPSHOT.jar"]
