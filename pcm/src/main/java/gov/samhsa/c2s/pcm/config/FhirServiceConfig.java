package gov.samhsa.c2s.pcm.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.IGenericClient;
import ca.uhn.fhir.validation.FhirValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FhirServiceConfig {

    @Value("${c2s.pcm.config.hie-connection.fhir.serverUrl}")
    String fhirServerUrl;

    @Value("${c2s.pcm.config.hie-connection.fhir.ClientSocketTimeoutInMs}")
    String fhirClientSocketTimeout;

    @Bean
    public FhirContext fhirContext(){
        FhirContext fhirContext = FhirContext.forDstu3();
        fhirContext.getRestfulClientFactory().setSocketTimeout(Integer.parseInt(fhirClientSocketTimeout));
        return fhirContext;
    }

    @Bean
    public IGenericClient fhirClient() {
        // Create a client
        return fhirContext().newRestfulGenericClient(fhirServerUrl);
    }

    @Bean
    public IParser fhirXmlParser() {
        return  fhirContext().newXmlParser();
    }

    @Bean
    public IParser fhirJsonParser() {
        return  fhirContext().newJsonParser();
    }

    @Bean
    public FhirValidator fhirValidator() {
        return  fhirContext().newValidator();
    }


}
