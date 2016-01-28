package gov.samhsa.mhc.pcm.infrastructure.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

public class LoggingSessionDestroyedEventListener implements
ApplicationListener<HttpSessionDestroyedEvent> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		logger.debug("Session is ended");
	}
}
