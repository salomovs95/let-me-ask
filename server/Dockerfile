FROM alpine:3.22 AS build

COPY mvnw .
COPY mvn/ .
COPY pom.xml .
COPY src/ .

RUN  ./mvnw clean -Dmaven.test.skip install

FROM eclipse-temurin:21.0.7_6-jre-ubi9-minimal
ENV SPRING_PROFILE 'prod'
COPY --from=build target/agents-0.0.1.jar app.jar

CMD ["java", "-jar", "app.jar"]
