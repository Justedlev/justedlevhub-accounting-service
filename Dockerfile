FROM openjdk:17
ARG JAR_FILE=./service/target/*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]