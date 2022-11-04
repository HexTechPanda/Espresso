FROM openjdk:11.0-jdk

VOLUME /tmp

ENV SPRING_PROFILES_ACTIVE=prod

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "/app.jar"]