FROM java:8
VOLUME /java
ADD pcm-1.6.0-SNAPSHOT.jar /pcm.jar
ENV JAVA_CACERTS /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security
ADD uaa.cer /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security
RUN keytool -import -noprompt -alias uaa -keystore  ${JAVA_CACERTS}/cacerts -storepass changeit -file ${JAVA_CACERTS}/uaa.cer
ENTRYPOINT ["java","-jar","/pcm.jar"]