FROM openjdk:23-slim

WORKDIR /app

COPY ./app.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
