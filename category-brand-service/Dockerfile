FROM openjdk:17-jdk-alpine
LABEL authors="aqazadeh"

COPY target/category-brand-service-1.0-BETA.jar category-brand-service-1.0-BETA.jar

ENV CONFIG_SERVER_URL=http://localhost:2121/

EXPOSE 8080

ENTRYPOINT ["java","-jar","/category-brand-service-1.0-BETA.jar"]
