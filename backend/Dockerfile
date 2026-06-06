# ===== build stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# FORÇA o compilador para Java 21 dentro do container
RUN mvn -DskipTests -Dmaven.compiler.release=21 -Djava.version=21 package

# ===== runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]