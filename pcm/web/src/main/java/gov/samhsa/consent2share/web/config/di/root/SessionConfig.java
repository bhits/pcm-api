package gov.samhsa.consent2share.web.config.di.root;

import gov.samhsa.consent2share.infrastructure.eventlistener.LoggingSessionDestroyedEventListener;
import gov.samhsa.consent2share.infrastructure.eventlistener.SessionTimeoutConfigSessionCreatedEventListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {
	@Value("${session_timeout_in_minutes}")
	private int sessionTimeoutInMins;

	// This bean must be in root application context so that org.springframework.security.web.session.HttpSessionEventPublisher can publish event to it
	// See HttpSessionEventPublisher code for details
	@Bean
	public SessionTimeoutConfigSessionCreatedEventListener sessionTimeoutConfigSessionCreatedEventListener() {
		SessionTimeoutConfigSessionCreatedEventListener sessionCreatedEventListener = new SessionTimeoutConfigSessionCreatedEventListener(sessionTimeoutInMins);
		return sessionCreatedEventListener;
	}

	@Bean
	public LoggingSessionDestroyedEventListener loggingSessionDestroyedEventListener() {
		LoggingSessionDestroyedEventListener loggingSessionDestroyedEventListener = new LoggingSessionDestroyedEventListener();
		return loggingSessionDestroyedEventListener;
	}
}
