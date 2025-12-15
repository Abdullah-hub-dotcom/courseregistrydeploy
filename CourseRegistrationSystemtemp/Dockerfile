# Use official Java 17 image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy your Spring Boot project files
COPY CourseRegistrationSystemtemp /app

# Install Maven and build
RUN apt-get update && apt-get install -y maven \
    && mvn clean package

# Expose port (Render will assign PORT)
EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-jar", "target/*.jar"]
