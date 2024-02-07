FROM openjdk:17-oracle AS build

WORKDIR /server

COPY pom.xml mvnw mvnw.cmd .

COPY .mvn/ .mvn/

RUN ./mvnw dependency:go-offline

COPY . .

RUN ./mvnw package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /server

COPY --from=build /server/target/transactionservice-0.0.1-SNAPSHOT.jar /server/transactionservice-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "transactionservice-0.0.1-SNAPSHOT.jar"]