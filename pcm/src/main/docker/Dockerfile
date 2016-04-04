FROM java:8
VOLUME /java
ENV JAVA_CACERTS /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security
ADD mhc.cer /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security
RUN keytool -import -noprompt -alias mhc -keystore  ${JAVA_CACERTS}/cacerts -storepass changeit -file ${JAVA_CACERTS}/mhc.cer
ARG finalName
ADD $finalName /pcm.jar
ENTRYPOINT ["java","-jar","/pcm.jar","--spring.profiles.active=docker"]