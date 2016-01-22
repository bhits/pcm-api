package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.infrastructure.security.ClamAVService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClamAVServiceConfig {

    @Value("${mhc.pcm.config.clamd.host}")
    private String clamdHost;

    @Value("${mhc.pcm.config.clamd.port}")
    private int clamdPort;

    @Value("${mhc.pcm.config.clamd.connTimeOut}")
    private int connTimeOut;

    @Bean
    public ClamAVService clamAVService(){
        return new ClamAVService(clamdHost, clamdPort, connTimeOut);
    }
}
