FROM openjdk:11-jdk
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN if [ -f Main.java ]; then echo 'Main.java exists' && javac Main.java; else echo 'Main.java not found' && exit 1; fi
CMD java Main
