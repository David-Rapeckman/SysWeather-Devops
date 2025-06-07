## SysWeather – Guia de Implantação

# Pré-requisitos
• Git
• SDKMAN (para gerenciar versões do Java)
• Maven
• Docker

# Clonar o repositório

git clone https://github.com/guurangel/SysWeather.git
cd SysWeather

- Instalar/selecionar Java 17 e verificar Maven

sdk install java 17.0.8-tem # se ainda não tiver instalado
sdk use java 17.0.8-tem # para usar nesta sessão
mvn -version # deve exibir Java 17.x e Maven 3.x

- Compilar o projeto

mvn clean compile

- Gerar o JAR (sem testes)

mvn clean package -DskipTests

* O JAR será gerado em target/sysweather-0.0.1-SNAPSHOT.jar *

- Construir a imagem Docker da aplicação
docker build -t sysweather-app .

- Criar a rede Docker dedicada
docker network create sysweather-network

- Subir o container MySQL

docker run -d --name sysweather_mysql
--network sysweather-network
--user 1000:1000
-e MYSQL_ROOT_PASSWORD=root
-e MYSQL_DATABASE=sysweatherdb
-e MYSQL_USER=sysweatheruser
-e MYSQL_PASSWORD=s525sf53a2
-p 3306:3306
-v mysql_data:/var/lib/mysql
mysql:8.0

- comando para rodar in line(SQL): 

docker run -d --name sysweather_mysql --network sysweather-network --user 1000:1000 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=sysweatherdb -e MYSQL_USER=sysweatheruser -e MYSQL_PASSWORD=s525sf53a2 -p 3306:3306 -v mysql_data:/var/lib/mysql mysql:8.0

-  Subir o container da aplicação SysWeather
docker run -d --name sysweather_app
--network sysweather-network
-e URL=jdbc:mysql://sysweather_mysql:3306/sysweatherdb
-p 8080:8080
sysweather-app

-  comando para subir in line(Java)

docker run -d --name sysweather_app --network sysweather-network -e URL=jdbc:mysql://sysweather_mysql:3306/sysweatherdb -p 8080:8080 sysweather-app

- Verificar imagens, containers e rede

docker images
docker ps
docker network ls

 - Acessar o Swagger UI
Abra no navegador:
http://localhost:8080/swagger-ui/index.html

- Validar o conteúdo do banco via bash
docker exec -it sysweather_mysql
mysql -u sysweatheruser -ps525sf53a2 sysweatherdb
mysql> SHOW TABLES;
mysql> SELECT * FROM municipio;



# Limpeza (opcional)
docker rm -f sysweather_app sysweather_mysql
docker volume rm mysql_data
docker network rm sysweather-network

===========================
