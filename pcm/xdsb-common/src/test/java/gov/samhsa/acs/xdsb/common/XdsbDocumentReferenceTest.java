package gov.samhsa.acs.xdsb.common;

import static org.junit.Assert.*;
import gov.samhsa.acs.xdsb.common.XdsbDocumentReference;

import org.junit.BeforeClass;
import org.junit.Test;

public class XdsbDocumentReferenceTest {

	private static XdsbDocumentReference xdsbDocumentReference;
	private static final String DOCUMENT_UNIQUE_ID = "111";
	private static final String REPOSITORY_UNIQUE_ID = "222";

	@BeforeClass
	public static void setUp() throws Exception {
		xdsbDocumentReference = new XdsbDocumentReference(DOCUMENT_UNIQUE_ID,
				REPOSITORY_UNIQUE_ID);
	}

	@Test
	public void testToString() {
		// Assert
		assertEquals(REPOSITORY_UNIQUE_ID + ":" + DOCUMENT_UNIQUE_ID,
				xdsbDocumentReference.toString());
	}

	@Test
	public void testEqualsObject() {
		// Arrange
		XdsbDocumentReference anotherXdsbDocumentReference = new XdsbDocumentReference(
				"111", "222");
		XdsbDocumentReference notEqualXdsbDocumentReference = new XdsbDocumentReference(
				"111", "aaa");
		XdsbDocumentReference notEqualXdsbDocumentReference2 = new XdsbDocumentReference(
				"aaa", "222");

		// Assert
		assertTrue(xdsbDocumentReference.equals(anotherXdsbDocumentReference));
		assertTrue(anotherXdsbDocumentReference.equals(xdsbDocumentReference));
		assertEquals(xdsbDocumentReference, anotherXdsbDocumentReference);
		assertEquals(anotherXdsbDocumentReference, xdsbDocumentReference);
		assertNotEquals(xdsbDocumentReference, notEqualXdsbDocumentReference);
		assertNotEquals(notEqualXdsbDocumentReference, xdsbDocumentReference);
		assertNotEquals(xdsbDocumentReference, notEqualXdsbDocumentReference2);
		assertNotEquals(notEqualXdsbDocumentReference2, xdsbDocumentReference);
	}
}
