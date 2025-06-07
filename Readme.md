# SysWeather – Guia de Implantação

## ✅ Pré-requisitos

- Git  
- SDKMAN (para gerenciar versões do Java)  
- Maven  
- Docker  

---

## 📥 Clonar o repositório
```bash
git clone https://github.com/guurangel/SysWeather.git  

cd SysWeather
```
---

## ☕ Instalar/Selecionar Java 17 e verificar Maven

```bash
sdk install java 17.0.8-tem       # caso ainda não tenha instalado  
sdk use java 17.0.8-tem  
mvn -version                      # deve exibir Java 17.x e Maven 3.x
```

---

## ⚙️ Compilar o projeto
```
mvn clean compile
```
---

## 📦 Gerar o JAR (sem testes)
```
mvn clean package -DskipTests
```
- O JAR será gerado em:  
 target/sysweather-0.0.1-SNAPSHOT.jar

---

## 🐳 Docker

### 🔨 Construir a imagem Docker da aplicação
```
docker build -t sysweather-app .
```
### 🌐 Criar a rede Docker dedicada
```
docker network create sysweather-network
```
### 🛢 Subir o container MySQL

- Para melhor organização, suba esse codigo em um terminal a parte, e todas as consultas do banco poderam ser efetuadas no mesmo
 
```
docker run -d --name sysweather_mysql \  
  --network sysweather-network \  
  --user 1000:1000 \  
  -e MYSQL_ROOT_PASSWORD=root \  
  -e MYSQL_DATABASE=sysweatherdb \  
  -e MYSQL_USER=sysweatheruser \  
  -e MYSQL_PASSWORD=s525sf53a2 \  
  -p 3306:3306 \  
  -v mysql_data:/var/lib/mysql \  
  mysql:8.0
```
**Comando in line (SQL):**
```
docker run -d --name sysweather_mysql --network sysweather-network --user 1000:1000 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=sysweatherdb -e MYSQL_USER=sysweatheruser -e MYSQL_PASSWORD=s525sf53a2 -p 3306:3306 -v mysql_data:/var/lib/mysql mysql:8.0
```
---

### 🚀 Subir o container da aplicação SysWeather

- Lembrando que esse codigo deverá ser efetuado no VSCode.

```
docker run -d --name sysweather_app \  
  --network sysweather-network \  
  -e URL=jdbc:mysql://sysweather_mysql:3306/sysweatherdb \  
  -p 8080:8080 \  
  sysweather-app
```
**Comando inline (Java):**
```
docker run -d --name sysweather_app --network sysweather-network -e URL=jdbc:mysql://sysweather_mysql:3306/sysweatherdb -p 8080:8080 sysweather-app
```
---

## 🔍 Verificar imagens, containers e rede
```
docker images       #verifica as imagens
docker ps           #verifica os containers
docker network ls   #verifica as networks
```
---

## 🌐 Acessar o Swagger UI

Acesse no navegador:

http://localhost:8080/swagger-ui/index.html

---

## 🧾 Acessar e Validar o conteúdo do banco

- Para acessar o banco de dados, rode o seguinte comando abaixo.

```
docker exec -it sysweather_mysql  
mysql -u sysweatheruser -ps525sf53a2 sysweatherdb
```
- Para verificar as infomações enviadas para o banco de dados rode os comandos abaixo 

```
SHOW TABLES;           # mostra todas as tabelas do banco de dados

SELECT * FROM **** ;   # para verificar os dados digite o nome da tabela onde estão os asteriscos
```
---

## 🧹 Limpeza (opcional)

- para parar e deletar os container rode :

```
docker rm -f sysweather_app sysweather_mysql  
docker volume rm mysql_data  
docker network rm sysweather-network
```
