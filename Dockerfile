FROM eclipse-temurin:21-jdk-alpine

RUN adduser -D -h /home/SysWeather SysUser

WORKDIR /app

ENV URL=${URL}

COPY target/*.jar app.jar

EXPOSE 8080

USER SysUser

ENTRYPOINT ["sh", "-c", "exec java -jar app.jar"]
