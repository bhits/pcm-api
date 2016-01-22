package gov.samhsa.mhc.pcm.web.di;

import gov.samhsa.mhc.consentgen.ConsentBuilder;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentExportService;
import gov.samhsa.mhc.pcm.service.consentexport.ConsentExportServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentExportServiceConfig {

    @Bean
    public ConsentExportService consentExportService(ConsentBuilder consentBuilder){
        return new ConsentExportServiceImpl(consentBuilder);
    }
}
