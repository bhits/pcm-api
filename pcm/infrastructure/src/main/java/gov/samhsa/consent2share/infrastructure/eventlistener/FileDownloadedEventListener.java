package gov.samhsa.consent2share.infrastructure.eventlistener;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityPredicateKey;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.audit.AuditException;

/**
 * The listener interface for receiving fileDownloadedEvent events. The class
 * that is interested in processing a fileDownloadedEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addFileDownloadedEventListener<code> method. When
 * the fileDownloadedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FileDownloadedEventEvent
 */
public class FileDownloadedEventListener extends SecurityEventListener {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new file downloaded event listener.
	 *
	 * @param eventService
	 *            the event service
	 * @param auditService
	 *            the audit service
	 */
	public FileDownloadedEventListener(EventService eventService,
			AuditService auditService) {
		super(eventService, auditService);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.eventlistener.EventListener#canHandle
	 * (java.lang.Object)
	 */
	@Override
	public boolean canHandle(Object event) {
		return event instanceof FileDownloadedEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.eventlistener.SecurityEventListener
	 * #handle(java.lang.Object)
	 */
	@Override
	public void handle(Object event) {
		super.handle(event);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.eventlistener.SecurityEventListener
	 * #audit(gov.samhsa.consent2share.domain.SecurityEvent)
	 */
	@Override
	public void audit(SecurityEvent event) {
		FileDownloadedEvent fileDownloadedEvent = (FileDownloadedEvent) event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("FileDownloadedEventListener",
					fileDownloadedEvent.getUserName(),
					SecurityAuditVerb.DOWNLOADS_FILE,
					fileDownloadedEvent.getFileDownloaded(), predicateMap);
		} catch (AuditException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
