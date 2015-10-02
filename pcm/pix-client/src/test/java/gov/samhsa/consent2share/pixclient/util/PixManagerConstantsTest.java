package gov.samhsa.consent2share.pixclient.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PixManagerConstantsTest {

	private static final String EXPECTED_GLOBAL_DOMAIN_ID = "EXPECTED_GLOBAL_DOMAIN_ID";
	private static final String EXPECTED_GLOBAL_DOMAIN_NAME = "EXPECTED_GLOBAL_DOMAIN_NAME";

	@Before
	public void setUp() throws Exception {
		// Arrange
		new PixManagerConstants(EXPECTED_GLOBAL_DOMAIN_ID,
				EXPECTED_GLOBAL_DOMAIN_NAME);
	}

	@Test
	public void testPixManagerConstants() {
		// Assert
		assertEquals(EXPECTED_GLOBAL_DOMAIN_ID,
				PixManagerConstants.GLOBAL_DOMAIN_ID);
		assertEquals(EXPECTED_GLOBAL_DOMAIN_NAME,
				PixManagerConstants.GLOBAL_DOMAIN_NAME);
	}
}
