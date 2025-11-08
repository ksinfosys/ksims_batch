FROM gradle:7.2-jdk11 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jdk

COPY expiration.sh expiration.sh ./
COPY --from=builder /app/build/libs/*.jar app.jar

# ENTRYPOINT ["java", "-jar", "/app.jar"]

RUN chmod +x /expiration.sh

ENTRYPOINT ["/expiration.sh"]