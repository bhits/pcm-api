package gov.samhsa.mhc.pcm.config;

import gov.samhsa.mhc.pcm.infrastructure.eventlistener.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventServiceConfig {

    @Bean
    public EventService eventService(){
        return new EventService();
    }
}
