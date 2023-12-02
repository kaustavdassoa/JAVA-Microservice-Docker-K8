FROM openjdk:19

ARG JAR_FILE=build/libs/EurekaServer-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} eurekaserver.jar

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]

EXPOSE 8761