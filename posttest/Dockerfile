# stage-1 build artifact
FROM amazoncorretto:21.0.2-alpine3.19 as builder
WORKDIR /app
ADD . .
RUN ["./gradlew","bootJar"]

# stage-2 running image
FROM gcr.io/distroless/java21-debian12:latest
WORKDIR /app
COPY --from=builder /app/build/libs/posttest-0.0.1-SNAPSHOT.jar posttest-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "posttest-0.0.1-SNAPSHOT.jar"]