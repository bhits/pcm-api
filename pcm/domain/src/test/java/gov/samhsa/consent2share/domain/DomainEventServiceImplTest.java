package gov.samhsa.consent2share.domain;

import static org.junit.Assert.*;
import java.lang.reflect.Field;

import org.junit.Test;

public class DomainEventServiceImplTest {

	@Test
	public void testRegisterAndRaise() throws Throwable {
		// Arrange
		DomainEventServiceImpl sut = new DomainEventServiceImpl();

		final String whatHappened = "Our team won!";

		AbstractDomainEventHandler<OccuredEvent> domainEventHandler = new AbstractDomainEventHandler<DomainEventServiceImplTest.OccuredEvent>() {
			private String receivedWhatHappened;

			@Override
			public void handle(OccuredEvent event) {
				receivedWhatHappened = event.getWhatHappened();
			}

			@Override
			protected Class<OccuredEvent> getEventClass() {
				return OccuredEvent.class;
			}
		};

		OccuredEvent occuredEvent = new OccuredEvent(whatHappened);

		// Act
		sut.register(domainEventHandler);
		sut.raise(occuredEvent);

		// Assert
		Field f = domainEventHandler.getClass().getDeclaredField(
				"receivedWhatHappened");
		f.setAccessible(true);
		String receivedWhatHappened = (String) (f.get(domainEventHandler));

		assertEquals(whatHappened, receivedWhatHappened);
	}

	private class OccuredEvent implements DomainEvent {
		private String whatHappened;

		public OccuredEvent(String whatHappened) {
			this.whatHappened = whatHappened;
		}

		public String getWhatHappened() {
			return whatHappened;
		}
	}
}