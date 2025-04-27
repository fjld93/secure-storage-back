FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/secure-storage-0.0.1-SNAPSHOT.jar app-secure-storage.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app-secure-storage.jar"]
