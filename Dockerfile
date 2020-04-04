FROM openjdk:oracle

WORKDIR /usr/src/jso-backend

COPY ./target ./

CMD ["java", "-jar", "/usr/src/jso-backend/demo-0.0.1-SNAPSHOT.jar"]