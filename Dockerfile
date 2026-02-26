# -------- Stage 1: build (Maven) --------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first to leverage dependency caching
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn -q -DskipTests clean package

# -------- Stage 2: runtime (JRE) --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the generated jar (adjust if your JAR name differs)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Optional: set timezone if needed
# ENV TZ=America/Vancouver

ENTRYPOINT ["java","-jar","app.jar"]