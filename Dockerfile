FROM openjdk:11-jre
WORKDIR /app
COPY ./target/melodia-service.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
