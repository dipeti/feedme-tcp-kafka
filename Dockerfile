FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/app
COPY ./target/feedme-0.0.1-SNAPSHOT.jar /opt/app/japp.jar
CMD ["java", "-jar", "/opt/app/japp.jar"]
