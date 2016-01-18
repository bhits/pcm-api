package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * The listener interface for receiving event events. The class that is
 * interested in processing a event event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addEventListener<code> method. When
 * the event event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EventService
 */
public abstract class EventListener implements InitializingBean {

	/** The log. */
	Logger log = LoggerFactory.getLogger(this.getClass());

	/** The event service. */
	EventService eventService;

	/**
	 * Instantiates a new event listener.
	 *
	 * @param eventService
	 *            the event service
	 */
	public EventListener(EventService eventService) {
		super();
		this.eventService = eventService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		eventService.registerListener(this);
	}

	/**
	 * Can handle.
	 *
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public abstract boolean canHandle(Object event);

	/**
	 * Handle.
	 *
	 * @param event
	 *            the event
	 */
	public abstract void handle(Object event);
}
