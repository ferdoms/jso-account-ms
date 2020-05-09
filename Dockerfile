FROM openjdk:oracle

WORKDIR /usr/src/accountms

COPY ./target ./

CMD ["java", "-jar", "/usr/src/accountms/jso-account-ms-0.0.1-SNAPSHOT.jar"]