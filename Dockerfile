FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/myapp.jar /app
CMD ["java", "-jar", "/app/myapp.jar"]