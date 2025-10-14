FROM eclipse-temurin:17-jdk

LABEL authors="yareach"

WORKDIR /app

COPY build/libs/*SNAPSHOT.jar app/app.jar

ENTRYPOINT ["java", "-jar", "app/app.jar"]