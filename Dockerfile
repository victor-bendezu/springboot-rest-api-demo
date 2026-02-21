# -------- Stage 1: build (Maven) --------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos primero pom para cache de dependencias
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src ./src
RUN mvn -q -DskipTests clean package

# -------- Stage 2: runtime (JRE) --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiamos el jar generado (ajusta si tu JAR se llama distinto)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Opcional: configurar timezone (por si quieres)
# ENV TZ=America/Vancouver

ENTRYPOINT ["java","-jar","app.jar"]