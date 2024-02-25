# stage-1 build artifact
FROM amazoncorretto:17.0.9-alpine3.18 as builder
WORKDIR /app
ADD . .
RUN ["./gradlew","bootJar"]

# stage-2 running image
FROM gcr.io/distroless/java17-debian12:latest
WORKDIR /app
COPY --from=builder /app/build/libs/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]