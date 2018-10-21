FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./build/libs/register-0.0.1-SNAPSHOT.jar app.jar
RUN echo 'Register Service running'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]