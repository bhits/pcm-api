package gov.samhsa.consent2share.domain.consent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.domain.AbstractDomainEventHandler;
import gov.samhsa.consent2share.domain.DomainEventManager;
import gov.samhsa.consent2share.domain.DomainEventServiceImpl;
import gov.samhsa.consent2share.domain.consent.event.ConsentRevokedEvent;

import org.junit.Test;

public class SignedPDFConsentRevocationTest {
	@Test
	public void testSetContent_SignedPDFConsentRevocation_Has_Same_Content_As_Given_Content() {
		// Arrange
		SignedPDFConsentRevocation sut = new SignedPDFConsentRevocation();
		byte[] content = new byte[] {};
		final Long consentId = (Long) 1L;

		// Act
		sut.setContent(content, consentId);

		// Assert
		assertEquals(content, sut.getContent());
	}

	@Test
	public void testSetContent_ConsentRevokedEvent_Is_Fired() {
		// Arrange
		SignedPDFConsentRevocation sut = new SignedPDFConsentRevocation();
		byte[] content = new byte[] {};
		final Long consentId = (Long) 1L;
		
		ConsentRevokedEventHandler domainEventHandler = new ConsentRevokedEventHandler(new ConsentRevokedEvent(consentId));
		
		DomainEventManager.registerDomainEventService(new DomainEventServiceImpl());
		DomainEventManager.register(domainEventHandler);

		// Act
		sut.setContent(content, consentId);

		// Assert
		assertTrue(domainEventHandler.isEventFired());
	}

	private class ConsentRevokedEventHandler extends
			AbstractDomainEventHandler<ConsentRevokedEvent> {
		private boolean eventFired = false;
		private ConsentRevokedEvent expectedEvent;

		public ConsentRevokedEventHandler(ConsentRevokedEvent expectedEvent) {
			this.expectedEvent = expectedEvent;
		}

		@Override
		protected Class<ConsentRevokedEvent> getEventClass() {
			return ConsentRevokedEvent.class;
		}

		@Override
		public void handle(ConsentRevokedEvent event) {
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