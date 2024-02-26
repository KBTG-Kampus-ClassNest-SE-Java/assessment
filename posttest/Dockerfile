FROM amazoncorretto:17-alpine-jdk as build
WORKDIR /usr/posttest

COPY gradlew .
COPY .gradle .gradle
COPY gradle gradle
COPY build.gradle .
COPY src src

RUN ./gradlew build -x test

FROM amazoncorretto:17-alpine-jdk
VOLUME /tmp
ARG JAR_FILE=posttest-0.0.1-SNAPSHOT.jar
WORKDIR /usr/posttest
COPY --from=build /usr/posttest/build/libs/${JAR_FILE} app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","app.jar"]