# Build the Spring Boot JAR
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

COPY mvnw ./
COPY .mvn/ .mvn/

COPY pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Create a image
FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
    