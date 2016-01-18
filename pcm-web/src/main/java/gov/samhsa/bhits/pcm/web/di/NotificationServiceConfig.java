package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.domain.patient.PatientRepository;
import gov.samhsa.bhits.pcm.service.notification.NotificationService;
import gov.samhsa.bhits.pcm.service.notification.NotificationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationServiceConfig {

    @Bean
    public NotificationService notificationService(PatientRepository patientRepository){
        return new NotificationServiceImpl(patientRepository);
    }
}
