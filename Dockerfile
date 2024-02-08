FROM openjdk:17-oracle AS build

WORKDIR /server

COPY pom.xml mvnw mvnw.cmd .

COPY .mvn/ .mvn/

RUN ./mvnw dependency:go-offline

COPY . .

RUN ./mvnw test

RUN ./mvnw package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /server

COPY --from=build /server/target/transactions-0.0.1-SNAPSHOT.jar /server/transactions-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transactions-0.0.1-SNAPSHOT.jar"]