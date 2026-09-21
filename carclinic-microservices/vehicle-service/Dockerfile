FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace

COPY vehicle-service/gradlew vehicle-service/
COPY vehicle-service/gradle vehicle-service/gradle
COPY vehicle-service/settings.gradle.kts vehicle-service/
COPY vehicle-service/build.gradle.kts vehicle-service/
COPY vehicle-service/src vehicle-service/src

RUN chmod +x vehicle-service/gradlew
RUN cd vehicle-service && ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/vehicle-service/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
