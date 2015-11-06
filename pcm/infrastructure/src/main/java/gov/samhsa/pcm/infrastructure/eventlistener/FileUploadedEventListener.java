package gov.samhsa.pcm.infrastructure.eventlistener;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.pcm.domain.SecurityEvent;
import gov.samhsa.pcm.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.pcm.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.pcm.infrastructure.securityevent.SecurityPredicateKey;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.audit.AuditException;

/**
 * The listener interface for receiving fileUploadedEvent events. The class that
 * is interested in processing a fileUploadedEvent event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addFileUploadedEventListener<code> method. When
 * the fileUploadedEvent event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FileUploadedEventEvent
 */
public class FileUploadedEventListener extends SecurityEventListener {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new file uploaded event listener.
	 *
	 * @param eventService
	 *            the event service
	 * @param auditService
	 *            the audit service
	 */
	public FileUploadedEventListener(EventService eventService,
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
		return event instanceof FileUploadedEvent;
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
		FileUploadedEvent fileUploadedEvent = (FileUploadedEvent) event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("FileUploadedEventListener",
					fileUploadedEvent.getUserName(),
					SecurityAuditVerb.UPLOADS_FILE,
					fileUploadedEvent.getFileUploaded(), predicateMap);
		} catch (AuditException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
