FROM openjdk:19

ARG JAR_FILE=build/libs/OrderService-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} orderservice.jar

ENTRYPOINT ["java", "-jar", "/orderservice.jar"]

EXPOSE 8001