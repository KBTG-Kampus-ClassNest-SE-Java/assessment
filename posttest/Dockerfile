FROM amazoncorretto:17 as builder

# Cache Gradle dependencies and build output
COPY .. .
RUN ["./gradlew", "build", "--build-cache"]

# Test Stage (with volume for reports)
FROM builder as test
RUN ["./gradlew", "test", "--build-cache"]
VOLUME /app/build/reports/tests

# Final Stage (inherits from the builder)
WORKDIR /app
COPY --from=builder /build/libs/posttest-0.0.1-SNAPSHOT.jar lottery-app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "lottery-app.jar"]