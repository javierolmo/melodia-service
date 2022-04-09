FROM openjdk:11-jre AS pfg-backend
WORKDIR /app
COPY ./target/melodia-service.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
