From openjdk:11

## Build stage
# FROM maven:latest as builder
# ADD ./pom.xml /application/pom.xml
# RUN mvn package -DskipTests

ADD ./target/*.jar  app.jar

ENTRYPOINT ["java", "-jar" , "app.jar"]

EXPOSE 8080




