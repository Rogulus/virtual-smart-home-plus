FROM maven:3-eclipse-temurin-17 AS builder

WORKDIR /virtual-smart-home-plus

COPY pom.xml .
COPY src ./src

RUN mvn package

FROM eclipse-temurin:17-alpine AS runtime

WORKDIR /virtual-smart-home-plus

USER 1000

COPY --from=builder /virtual-smart-home-plus/target/virtual-smart-home-plus-?.?.?-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
