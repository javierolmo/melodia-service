# Build pfg-backend
FROM maven:3.8.1-jdk-11

COPY ./pfg-backend /app/pfg/pfg-backend
COPY ./pfg-commons /app/pfg/pfg-commons

WORKDIR /app/pfg
RUN cd /app/pfg/pfg-commons && mvn install
RUN cd /app/pfg/pfg-backend && mvn package -DskipTests

# Run pfg-backend
FROM openjdk:11-jre AS pfg-backend
WORKDIR /app
COPY --from=0 /app/pfg/pfg-backend/target/pfg-backend.war /app/app.war
ENTRYPOINT ["java","-jar","app.war"]
EXPOSE 8080
