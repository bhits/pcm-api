FROM java:8
VOLUME /java
ADD pcm-1.6.0-SNAPSHOT.jar /pcm.jar
ENTRYPOINT ["java","-Djavax.net.ssl.trustStore=/java/keystore/mhc.keystore","-Djavax.net.ssl.trustStorePassword=changeit","-jar","/pcm.jar"]