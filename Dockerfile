# Use an official Maven image as the base image
FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /patriot-data-generator
COPY patriot-data-generator/pom.xml .
COPY patriot-data-generator/src ./src
RUN mvn clean install -DskipTests=true

WORKDIR /virtual-smart-home-plus
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests=true


#FROM patriotframework/simulator-base:latest AS runtime
#FROM eclipse-temurin:17-alpine AS runtime
FROM quay.io/mijaros/patriot-base:java-17 AS runtime

WORKDIR /virtual-smart-home-plus

USER 1000

COPY --from=builder /virtual-smart-home-plus/target/virtual-smart-home-plus.jar app.jar

EXPOSE 8080
#EXPOSE 5683

CMD ["java", "-jar", "app.jar"]
