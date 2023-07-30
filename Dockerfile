FROM gradle:6.8.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
EXPOSE 8080 8077
USER root
RUN mkdir /app
RUN mkdir -pv /app /build/reports /build/test-results
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
COPY --from=build /home/gradle/src/build/reports /build/reports/
COPY --from=build /home/gradle/src/build/test-results /build/test-results/
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]