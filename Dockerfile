# Etapa de build
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
# copia tudo que está dentro da pasta SysWeather para /app
COPY SysWeather/ .  
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app

# cria usuário não-root
RUN useradd -m sysuser
USER sysuser

# copia o JAR gerado na etapa de build
COPY --from=build /app/target/sysweather-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]