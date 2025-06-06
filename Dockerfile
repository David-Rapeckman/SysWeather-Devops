# Etapa de build
FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Criação de usuário não-root
RUN useradd -m sysuser
USER sysuser

# Variável de ambiente
ENV URL=${URL}

# Copia o .jar gerado
COPY --from=build /app/target/sysweather-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
