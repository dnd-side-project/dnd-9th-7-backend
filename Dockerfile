FROM gradle:8.9-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle build -x test

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /home/gradle/project/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]