FROM maven:3.3.3-jdk-8 as build

MAINTAINER Yiannis Mouchakis <gmouchakis@iit.demokritos.gr>

COPY . /tmp
WORKDIR /tmp

RUN mvn clean package

FROM tomcat:8-alpine

COPY --from=build /tmp/target/fedx-*.war /usr/local/tomcat/webapps

EXPOSE 8080

