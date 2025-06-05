SysWeather - Projeto Java + MySQL + Docker
==========================================

SysWeather é uma aplicação Java (Spring Boot) para monitoramento climático, com persistência de dados em MySQL, totalmente conteinerizada via Docker.

1. PRÉ-REQUISITOS
------------------
- Docker instalado
- Maven (ou ./mvnw) para gerar o .jar

2. BUILD DO PROJETO JAVA
-------------------------
Entre na pasta do projeto e execute:

    ./mvnw clean package

Isso gerará um arquivo .jar dentro da pasta 'target/'.

3. DOCKERFILE
--------------
Certifique-se de ter um arquivo Dockerfile com o seguinte conteúdo:

    FROM eclipse-temurin:21-jdk-alpine
    RUN adduser -D -h /home/SysWeather SysUser
    WORKDIR /app
    ENV URL=${URL}
    COPY target/*.jar app.jar
    EXPOSE 8080
    USER SysUser
    ENTRYPOINT ["sh", "-c", "exec java -jar app.jar"]

4. BUILD DA IMAGEM DOCKER
--------------------------
Execute:

    docker build -t sysweather-app .

5. CRIAR REDE DOCKER
---------------------
    docker network create sysweather-network

6. SUBIR O CONTAINER MYSQL
---------------------------
    docker run -d --name sysweather_mysql \\
      --network sysweather-network \\
      --user 1000:1000 \\
      -e MYSQL_ROOT_PASSWORD=root \\
      -e MYSQL_DATABASE=sysweatherdb \\
      -e MYSQL_USER=sysweatheruser \\
      -e MYSQL_PASSWORD=s525sf53a2 \\
      -p 3306:3306 \\
      -v mysql_data:/var/lib/mysql \\
      mysql:8.0

7. SUBIR O CONTAINER DA APLICAÇÃO JAVA
---------------------------------------
    docker run -d --name sysweather_app \\
      --network sysweather-network \\
      -e URL=jdbc:mysql://sysweather_mysql:3306/sysweatherdb \\
      -p 8080:8080 \\
      sysweather-app

8. ACESSAR A API
-----------------
Acesse http://localhost:8080 no navegador ou via Postman.

9. LIMPAR TUDO (opcional)
--------------------------
    docker stop sysweather_app sysweather_mysql
    docker rm sysweather_app sysweather_mysql
    docker volume rm mysql_data
    docker network rm sysweather-network

AUTORES: David, Felipe e Gustavo – Projeto FIAP - SysWeather