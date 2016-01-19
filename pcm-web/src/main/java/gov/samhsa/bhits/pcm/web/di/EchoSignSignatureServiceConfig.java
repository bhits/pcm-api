package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.consent.ConsentRepository;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.bhits.pcm.infrastructure.EchoSignSignatureServiceImpl;
import gov.samhsa.bhits.pcm.service.esignaturepolling.EchoSignPollingService;
import gov.samhsa.bhits.pcm.service.esignaturepolling.EsignaturePollingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EchoSignSignatureServiceConfig {

    @Value("${bhits.pcm.config.echosign.echoSignDocumentServiceEndpointAddress}")
    private String echoSignDocumentServiceEndpointAddress;

    @Value("${bhits.pcm.config.echosign.echoSignApiKey}")
    private String echoSignApiKey;

    @Bean
    public EchoSignSignatureService echoSignSignatureService() {
        return new EchoSignSignatureServiceImpl(echoSignDocumentServiceEndpointAddress, echoSignApiKey);
    }

    @Bean
    public EsignaturePollingService esignaturePollingService(ConsentRepository consentRepository) {
        return new EchoSignPollingService(consentRepository, echoSignSignatureService());
    }
}
