FROM ubuntu:latest
LABEL authors="aliya.hadi"

ENTRYPOINT ["top", "-b"]

# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Maven wrapper + pom first for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw -q -DskipTests clean package

# ---- Run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create data dir for H2 file DB
RUN mkdir -p /data

EXPOSE 8080

# Optional: can pass JAVA_OPTS at runtime
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
