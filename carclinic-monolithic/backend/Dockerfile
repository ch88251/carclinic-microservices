# Dockerfile for carclinic-api (Spring Boot)

# --- Build stage ---
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN ./gradlew --version

COPY src ./src
RUN ./gradlew bootJar --no-daemon

# --- Runtime stage ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/carclinic-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
