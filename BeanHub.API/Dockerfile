FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/api-0.0.1-SNAPSHOT.jar api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]