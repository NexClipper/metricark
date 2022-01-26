# FROM maven:3.6-jdk-8-slim AS build
# COPY api/src /home/app/src
# COPY api/pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jre-alpine
ENV LANG ko_KR.UTF-8
ENV LANGUAGE ko_KR:en
ENV LC_ALL ko_KR.UTF-8

# MAINTAINER NexCloud

VOLUME /tmp
COPY  target/*.war /usr/local/lib/app.war
#ADD ./target/NexclipperAPI-0.0.1.war NexclipperAPI.war
#RUN sh -c 'touch /NexclipperAPI.war'

# COPY README.md /home/README.md
ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.131-3.b12.el7_3.x86_64/jre/
ENV PATH=$JAVA_HOME/bin:$PATH
  
ENV JAVA_OPTS=""
CMD exec java -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=70 -XX:NewRatio=1 -XX:SurvivorRatio=6 -XX:G1ReservePercent=10 -XX:+CMSClassUnloadingEnabled -XX:+CMSPermGenSweepingEnabled -jar  -Djava.security.egd=file:/dev/./urandom -jar /usr/local/lib/app.war