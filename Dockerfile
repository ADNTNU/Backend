# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy only the built JAR file into the container
COPY target/FlightFinderAPI.jar /usr/src/app

EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "FlightFinderAPI.jar"]