#FROM openjdk:8-jdk-alpine
#FROM openjdk:8-jdk-slim
FROM openjdk:11-jre-slim
VOLUME /tmp
LABEL maintainer="barsamms@gmail.com"

ENV SERVER_PORT 9001
ENV SPRING_PROFILES_ACTIVE dev

ENV SPRING_BOOT_USER fuelrod
ENV SPRING_BOOT_GROUP fuelrod
ENV TZ=Africa/Nairobi

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN apt-get update && apt-get install -y curl && apt-get install -y iputils-ping


RUN useradd $SPRING_BOOT_USER && usermod -aG $SPRING_BOOT_GROUP $SPRING_BOOT_USER && \
chmod 555 docker-entrypoint.sh && sh -c 'touch /app.jar'

COPY build/libs/fuelrod*.jar /app.jar
COPY src/main/resources/logback-spring.xml /src/main/resources/logback-spring.xml

#COPY src/main/resources/keystore.jks keystore.jks


RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE $SERVER_PORT
ENTRYPOINT ["./docker-entrypoint.sh"]