FROM openjdk:11-jdk
COPY ./target/todo-app-project-1.0-SNAPSHOT.jar /app/todo-app-project-1.0-SNAPSHOT.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todo-app-project-1.0-SNAPSHOT.jar"]