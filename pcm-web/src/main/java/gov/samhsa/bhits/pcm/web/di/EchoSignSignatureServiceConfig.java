package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EchoSignSignatureServiceConfig {

    @Value("${bhits.pcm.config.echosign.echoSignDocumentService15EndpointAddress}")
    private String echoSignDocumentService15EndpointAddress;

    @Value("${bhits.pcm.config.echosign.echoSignApiKey}")
    private String echoSignApiKey;

    @Bean
    public EchoSignSignatureService echoSignSignatureService() {
        return new EchoSignSignatureServiceImpl(echoSignDocumentService15EndpointAddress, echoSignApiKey);
    }
}
