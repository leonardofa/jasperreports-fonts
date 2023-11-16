FROM eclipse-temurin:17-jre-alpine
VOLUME /reports
COPY src/main/resources/reports /reports
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]