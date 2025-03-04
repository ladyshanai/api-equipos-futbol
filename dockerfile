FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/equiposfutbol-0.0.1-SNAPSHOT.jar  /app/equiposfutbol-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/equiposfutbol-0.0.1-SNAPSHOT.jar"]
