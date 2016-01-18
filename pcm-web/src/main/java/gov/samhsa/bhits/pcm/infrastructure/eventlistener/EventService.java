package gov.samhsa.bhits.pcm.infrastructure.eventlistener;

import gov.samhsa.bhits.pcm.domain.SecurityEvent;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class EventService.
 */
public class EventService implements
        org.springframework.context.ApplicationListener {

    /**
     * The listeners.
     */
    List<EventListener> listeners = new ArrayList<EventListener>();

    /**
     * Method that allows registering of an Event Listener.
     *
     * @param listener the listener
     */
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Spring executes this method with the event object. This method iterates
     * though the list of registered Listeners and checks whether any listener
     * can handle the event. Calls handle method of the Listener if it can
     * handle the event.
     *
     * @param event the event
     */
    public void onApplicationEvent(ApplicationEvent event) {
        dispatchEvent(event);
    }

    /**
     * Raise security event.
     *
     * @param securityEvent the security event
     */
    public void raiseSecurityEvent(SecurityEvent securityEvent) {
        dispatchEvent(securityEvent);
    }

    /**
     * Dispatch event.
     *
     * @param event the event
     */
    private void dispatchEvent(Object event) {
        for (EventListener listener : listeners) {
            if (listener.canHandle(event)) {
                listener.handle(event);
            }
        }
    }
}
