package gov.samhsa.bhits.pcm.web.di;

import gov.samhsa.bhits.pcm.infrastructure.eventlistener.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventServiceConfig {

    @Bean
    public EventService eventService(){
        return new EventService();
    }
}
