FROM openjdk:17-jdk-alpine
LABEL authors="aqazadeh"

COPY target/cart-service-1.0-BETA.jar cart-service-1.0-BETA.jar

ENV CONFIG_SERVER_URL=http://localhost:2121/

EXPOSE 8080

ENTRYPOINT ["java","-jar","/cart-service-1.0-BETA.jar"]
