# Build
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Run
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build /app/target/secure-storage-0.0.1-SNAPSHOT.jar app-secure-storage.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app-secure-storage.jar"]

