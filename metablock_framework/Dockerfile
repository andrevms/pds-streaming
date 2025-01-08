# Etapa de construção
FROM maven:3.9.9-amazoncorretto-17-debian-bookworm AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o pom.xml e o código fonte para o container
COPY pom.xml .
COPY src ./src

# Execute o Maven para compilar a aplicação
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR gerado da etapa de construção
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-Xms1g", "-Xmx2g", "-jar", "app.jar"]

