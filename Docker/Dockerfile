FROM maven:3.8.1-openjdk-15-slim
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn clean install
ENTRYPOINT ["./Docker/Entrypoint.sh"]
