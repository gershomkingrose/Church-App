# Use Java 21
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Run the jar
CMD ["java", "-jar", "target/churchflow-1.0.0.jar"]
