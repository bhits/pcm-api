FROM bhits/java-jar-runner:8u102-jre
VOLUME /java
ARG finalName
ENV JAR_FILE $finalName
COPY $finalName /
ENV DEFAULT_PROGRAM_ARGS --spring.profiles.active=docker