FROM tomcat:9.0.54-jdk11-openjdk
LABEL tag="1.0"

COPY target/*.war /usr/local/tomcat/webapps/

EXPOSE 8080

