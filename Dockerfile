FROM 003976973367.dkr.ecr.us-east-1.amazonaws.com/openjdk:v1.0
# Refer to Maven build -> finalName
ARG JAR_FILE=target/abacom-1.0.jar
ARG SCRIPT=run.sh

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

RUN apt-get update \
 && apt-get install -y curl unzip less jq
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
 && unzip awscliv2.zip \
 && sh ./aws/install
 
COPY ${SCRIPT} /usr/bin/local/run.sh

ENTRYPOINT ["/bin/bash", "/usr/bin/local/run.sh"]

# java -jar /opt/app/app.jar
# ENTRYPOINT ["java","-jar","app.jar"]