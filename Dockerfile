FROM maven:3.5-jdk-8-alpine
MAINTAINER Wavelabs Team
EXPOSE 8090
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/access-management-domain-apis-0.0.1-SNAPSHOT.jar access-management-domain-apis-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/access-management-domain-apis-0.0.1-SNAPSHOT.jar"]
