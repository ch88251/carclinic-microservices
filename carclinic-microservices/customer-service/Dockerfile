FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace

COPY customer-service/gradlew customer-service/
COPY customer-service/gradle customer-service/gradle
COPY customer-service/settings.gradle.kts customer-service/
COPY customer-service/build.gradle.kts customer-service/
COPY customer-service/src customer-service/src

RUN chmod +x customer-service/gradlew
RUN cd customer-service && ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/customer-service/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
