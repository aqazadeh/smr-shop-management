FROM openjdk:17-jdk-alpine
LABEL authors="aqazadeh"

COPY target/cart-service-0.0.1-.jar cart-service-0.0.1-SNAPSHOT.jar
ENV CONFIG_SERVER_URL http://localhost:2121/
EXPOSE 8100
ENTRYPOINT ["java","-jar","/cart-service-0.0.1-SNAPSHOT.jar"]
