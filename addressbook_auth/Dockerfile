FROM eclipse-temurin:17.0.4_8-jdk
WORKDIR /app
RUN mkdir /app/config
COPY target/*.jar /app/app.jar
COPY users/users.json /app/users/users.json
ENTRYPOINT ["java", "-jar", "/app/app.jar"]