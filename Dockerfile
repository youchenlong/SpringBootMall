FROM openjdk:8
EXPOSE 8080
ADD ${project.build.finalName}.jar /SpringBootMall-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/SpringBootMall-0.0.1-SNAPSHOT.jar"]
MAINTAINER youchenlong