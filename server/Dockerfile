FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

COPY pom.xml .
COPY src/ .

RUN  mvn clean -Dmaven.test.skip install

FROM eclipse-temurin:21.0.7_6-jre-ubi9-minimal
ENV SPRING_PROFILE 'prod'
COPY --from=build target/agents-0.0.1.jar app.jar

CMD ["java", "-jar", "app.jar"]
