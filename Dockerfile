# Building the App with Maven
FROM maven:3.6.1-jdk-8-slim

RUN mkdir /app
WORKDIR /app

# Run Maven build
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

COPY src src
RUN mvn package

FROM tomcat:8

RUN rm -rf /usr/local/tomcat/webapps/ROOT/*

COPY --from=0 /app/target/httper-server/ /usr/local/tomcat/webapps/ROOT
COPY docker-entrypoint.sh /usr/local

RUN chmod +x /usr/local/docker-entrypoint.sh

ENV DB_SERVER="127.0.0.1"
ENV DB_PORT="3306"
ENV DB_NAME="httper"
ENV DB_USER="www"
ENV DB_PASSWORD=""

ENTRYPOINT ["/usr/local/docker-entrypoint.sh"]
