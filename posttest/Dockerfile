#stage-1 build artifact
FROM amazoncorretto:17.0.9-alpine3.18 as builder
WORKDIR /app
ADD . .
RUN chmod +x ./gradlew
RUN ["./gradlew", "bootJar"]


FROM gcr.io/distroless/java17-debian12:latest
WORKDIR /app
EXPOSE 8888
COPY --from=builder /app/build/libs/posttest-0.0.1-SNAPSHOT.jar posttest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "posttest-0.0.1-SNAPSHOT.jar"]