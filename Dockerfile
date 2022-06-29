FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE='spring-web-app.jar'
WORKDIR /APP
COPY /target/${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]