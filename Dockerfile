FROM amazoncorretto:21.0.2-alpine3.18

WORKDIR /app
ADD . .
RUN ["./gradlew","bootJar"]

ENTRYPOINT ["java", "-jar", "build/libs/posttest-0.0.1-SNAPSHOT.jar"]