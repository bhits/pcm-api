package gov.samhsa.consent2share.domain.consent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.domain.AbstractDomainEventHandler;
import gov.samhsa.consent2share.domain.DomainEventManager;
import gov.samhsa.consent2share.domain.DomainEventServiceImpl;
import gov.samhsa.consent2share.domain.consent.event.ConsentSignedEvent;

import org.junit.Test;

public class SignedPDFConsentTest {

	@Test
	public void testSetContent_SignedPDFConsent_Has_Same_Content_As_Given_Content() {
		// Arrange
		SignedPDFConsent sut = new SignedPDFConsent();
		byte[] content = new byte[] {};
		final Long consentId = (Long) 1L;

		// Act
		sut.setContent(content, consentId);

		// Assert
		assertEquals(content, sut.getContent());
	}

	@Test
	public void testSetContent_ConsentSignedEvent_Is_Fired() {
		// Arrange
		SignedPDFConsent sut = new SignedPDFConsent();
		byte[] content = new byte[] {};
		final Long consentId = (Long) 1L;
		
		ConsentSignedEventHandler domainEventHandler = new ConsentSignedEventHandler(new ConsentSignedEvent(consentId));
		
		DomainEventManager.registerDomainEventService(new DomainEventServiceImpl());
		DomainEventManager.register(domainEventHandler);

		// Act
		sut.setContent(content, consentId);

		// Assert
		assertTrue(domainEventHandler.isEventFired());
	}

	private class ConsentSignedEventHandler extends
			AbstractDomainEventHandler<ConsentSignedEvent> {
		private boolean eventFired = false;
		private ConsentSignedEvent expectedEvent;

		public ConsentSignedEventHandler(ConsentSignedEvent expectedEvent) {
			this.expectedEvent = expectedEvent;
		}

		@Override
		protected Class<ConsentSignedEvent> getEventClass() {
			return ConsentSignedEvent.class;
		}

		@Override
		public void handle(ConsentSignedEvent event) {
			eventFired = false;
			if (event.getConsentId().equals(expectedEvent.getConsentId())) {
				eventFired = true;
			}
		}

		public boolean isEventFired() {
			return eventFired;
		}
	}
}
