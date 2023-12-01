FROM openjdk:19

ARG JAR_FILE=build/libs/ProductService-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} productservice.jar

ENTRYPOINT ["java", "-jar", "/productservice.jar"]

EXPOSE 9001