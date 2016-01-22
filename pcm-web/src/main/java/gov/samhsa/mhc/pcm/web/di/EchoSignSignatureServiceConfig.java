package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.pcm.domain.consent.ConsentRepository;
import gov.samhsa.mhc.pcm.infrastructure.EchoSignSignatureService;
import gov.samhsa.mhc.pcm.infrastructure.EchoSignSignatureServiceImpl;
import gov.samhsa.mhc.pcm.service.esignaturepolling.EchoSignPollingService;
import gov.samhsa.mhc.pcm.service.esignaturepolling.EsignaturePollingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EchoSignSignatureServiceConfig {

    @Value("${mhc.pcm.config.echosign.echoSignDocumentServiceEndpointAddress}")
    private String echoSignDocumentServiceEndpointAddress;

    @Value("${mhc.pcm.config.echosign.echoSignApiKey}")
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
