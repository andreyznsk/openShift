FROM eclipse-temurin:17.0.4_8-jdk
WORKDIR /app
RUN mkdir /app/config
COPY target/*.jar /app/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app/app.jar