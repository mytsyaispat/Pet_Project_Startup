FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=./build/libs/startup-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} startup.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/startup.jar"]