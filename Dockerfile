FROM openjdk

RUN mkdir /address-app

COPY restapi-0.0.1-SNAPSHOT.jar /address-app/

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/address-app/restapi-0.0.1-SNAPSHOT.jar" ]