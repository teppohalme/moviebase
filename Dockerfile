# Use Eclipse Temurin JDK 21 (compatible and Render-safe)
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy everything to /app
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port 8080 for Render
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/moviebase-0.0.1-SNAPSHOT.jar"]
