FROM openjdk:8-jre-alpine

VOLUME /tmp
ADD build/libs/slack-svmeal-*.jar slackSvmeal.jar
RUN sh -c 'touch /slackSvmeal.jar'
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /slackSvmeal.jar"]