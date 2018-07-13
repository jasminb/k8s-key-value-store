FROM maven:3.3-jdk-8-onbuild AS BUILD_IMAGE

WORKDIR /tmp

# Copy pom and sources from host filesystem into /tmp (WORKING_DIR)
COPY pom.xml .
COPY src ./src

# Build the JAR
RUN bash -l -c "mvn clean install"

FROM java:8

# Copy the built JAR file from the previous image
COPY --from=BUILD_IMAGE /tmp/target/*.jar ws.jar

# Expose port that service listens on
EXPOSE 8080

# Run the JAR
CMD ["java","-jar","ws.jar"]