FROM openjdk:11
COPY ./target/*.jar /app.jar
CMD ["--server.port=8062"]
EXPOSE 8062
ENTRYPOINT ["java","-jar","/app.jar"]