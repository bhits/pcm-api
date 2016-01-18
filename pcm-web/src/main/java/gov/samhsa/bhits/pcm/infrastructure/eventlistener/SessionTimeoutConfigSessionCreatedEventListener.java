package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;

public class SessionTimeoutConfigSessionCreatedEventListener implements
ApplicationListener<HttpSessionCreatedEvent> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private int sessionTimeoutInMins;

	public SessionTimeoutConfigSessionCreatedEventListener(int SessionTimeoutInMins) {
		this.sessionTimeoutInMins = SessionTimeoutInMins;
	}

	@Override
	public void onApplicationEvent(HttpSessionCreatedEvent event) {
		int sessionTimeoutInSecs = sessionTimeoutInMins * 60;
		event.getSession().setMaxInactiveInterval(sessionTimeoutInSecs);

		logger.debug("Session Timeout is set as " + sessionTimeoutInSecs + " Seconds");
	}
}
