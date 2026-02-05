# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# cache dependencies first
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# then copy source and build
COPY src ./src
RUN mvn -q -DskipTests clean package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]



#FROM ubuntu:latest
#LABEL authors="aliya.hadi"
#
#ENTRYPOINT ["top", "-b"]
#
## ---- Build stage ----
##FROM eclipse-temurin:17-jdk AS build
##WORKDIR /app
##====
#FROM maven:3.9.6-eclipse-temurin-17 AS builder
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src ./src
#RUN mvn clean package -DskipTests
##====
#
## Copy Maven wrapper + pom first for caching
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#RUN chmod +x mvnw
#RUN ./mvnw -q -DskipTests dependency:go-offline
#
## Copy source and build
#COPY src src
#RUN ./mvnw -q -DskipTests clean package
#
## ---- Run stage ----
##FROM eclipse-temurin:17-jre
##WORKDIR /app
#
## ---- Runtime stage ----
#FROM eclipse-temurin:17-jre
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app/app.jar"]
#
## Copy jar from build stage
#COPY --from=build /app/target/*.jar app.jar
#
## Create data dir for H2 file DB
#RUN mkdir -p /data
#
#EXPOSE 8080
#
## Optional: can pass JAVA_OPTS at runtime
#ENV JAVA_OPTS=""
#
#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
