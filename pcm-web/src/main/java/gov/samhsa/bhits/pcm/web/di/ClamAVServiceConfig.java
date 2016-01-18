package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.infrastructure.security.ClamAVService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClamAVServiceConfig {

    @Value("${bhits.pcm.config.clamd.host}")
    private String clamdHost;

    @Value("${bhits.pcm.config.clamd.port}")
    private int clamdPort;

    @Value("${bhits.pcm.config.clamd.connTimeOut}")
    private int connTimeOut;

    @Bean
    public ClamAVService clamAVService(){
        return new ClamAVService(clamdHost, clamdPort, connTimeOut);
    }
}
