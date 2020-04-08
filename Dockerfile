FROM openjdk:oracle

WORKDIR /usr/src/accountms

COPY ./target ./

CMD ["java", "-jar", "/usr/src/accountms/accountms-0.0.1-SNAPSHOT.jar"]