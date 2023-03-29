FROM openjdk:11

WORKDIR /app

# Copy the source code into the container
COPY . .

# Compile the code
RUN javac Main.java

# Run the code
CMD ["java", "Main"]