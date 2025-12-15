# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project files
COPY src ./src

# Build the Spring Boot app
RUN mvn clean package -DskipTests

# Expose port (matches your application.properties)
EXPOSE 9000

# Run the jar
CMD ["java", "-jar", "target/CourseRegistrationSystem-0.0.1-SNAPSHOT.jar"]
