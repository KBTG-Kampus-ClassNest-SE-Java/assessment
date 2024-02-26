FROM openjdk:17-jdk-slim as build

ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle

RUN chmod +x gradlew
RUN ./gradlew build || return 0

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:17-jdk-slim

ENV JAR_FILE=posttest-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY --from=build $APP_HOME/build/libs/$JAR_FILE .

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "posttest-0.0.1-SNAPSHOT.jar"]