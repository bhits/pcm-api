FROM bhits/java-jar-runner:8u102-jre
VOLUME /java

# Copy application jar to container, argument is defined in pom.xml
ARG finalName
ENV JAR_FILE $finalName
COPY $finalName /

# Default active docker profile
ENV DEFAULT_PROGRAM_ARGS --spring.profiles.active=docker