FROM gradle:5.6.0-jdk12

COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

RUN gradle fatJar --no-daemon
RUN sh -c 'cp build/libs/*.jar /bot.jar'
RUN touch /config.properties

WORKDIR /

CMD ["java", "-jar", "/bot.jar"]
