FROM tomcat:8

MAINTAINER Yiannis Mouchakis <gmouchakis@iit.demokritos.gr>

RUN apt-get update && apt-get install -y git openjdk-7-jdk maven && \
    cd /opt && \
    git clone https://github.com/semagrow/docker-fedx-server.git && cd docker-fedx-server && \
    mvn clean package && \
    cd /opt/docker-fedx && \
    cp target/fedx-*.war /usr/local/tomcat/webapps/ && \
    cd / && rm -r /opt/docker-fedx && rm -r /root/.m2 && \
    apt-get --purge remove -y git openjdk-7-jdk maven && apt-get --purge autoremove -y && apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

CMD catalina.sh run
