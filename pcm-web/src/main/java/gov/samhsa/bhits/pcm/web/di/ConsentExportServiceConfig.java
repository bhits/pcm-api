package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.consentgen.ConsentBuilder;
import gov.samhsa.bhits.pcm.service.consentexport.ConsentExportService;
import gov.samhsa.bhits.pcm.service.consentexport.ConsentExportServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentExportServiceConfig {

    @Bean
    public ConsentExportService consentExportService(ConsentBuilder consentBuilder){
        return new ConsentExportServiceImpl(consentBuilder);
    }
}
