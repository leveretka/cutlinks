FROM java:8-jre
MAINTAINER Marharyta Nedzelska <margoqueen95@gmail.com>
ADD ./target/cutlinks.jar /app/
CMD ["java", "-jar", "/app/cutlinks.jar"]
EXPOSE 8080
