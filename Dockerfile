FROM gradle:7.2-jdk11 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y dos2unix

COPY execute.sh ./

RUN dos2unix execute.sh
RUN sed -i '1s/^\xEF\xBB\xBF//' execute.sh

COPY --from=builder /app/build/libs/*.jar app.jar

# ENTRYPOINT ["java", "-jar", "/app.jar"]

RUN chmod +x execute.sh

ENTRYPOINT ["sh", "./execute.sh", "KSBAT_PT001"]