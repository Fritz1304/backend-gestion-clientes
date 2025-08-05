# Etapa 1: Compilar la aplicación con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final y ligera con solo el JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copiamos el .jar compilado de la etapa anterior
COPY --from=build /app/target/backend-gestion-clientes-0.0.1-SNAPSHOT.jar app.jar
# Exponemos el puerto que usa Spring Boot
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
