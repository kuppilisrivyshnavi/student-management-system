# Use Maven with Java 17
FROM maven:3.9.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build and test the project
RUN mvn clean package

# Keep the generated JAR available
CMD ["bash"]
