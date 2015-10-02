package gov.samhsa.acs.common.namespace;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PepNamespaceContextTest {
	public static final String HL7_PREFIX = "hl7";
	public static final String HL7_URI = "urn:hl7-org:v3";
	public static final String XENC_PREFIX = "xenc";
	public static final String XENC_URI = "http://www.w3.org/2001/04/xmlenc#";
	public static final String RIM_PREFIX = "rim";
	public static final String RIM_URI = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0";
	public static final String QUERY_PREFIX = "query";
	public static final String QUERY_URI = "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0";
	public static final String IHE_ITI_XDSB_2007_PREFIX = "xdsb";
	public static final String IHE_ITI_XDSB_2007_URI= "urn:ihe:iti:xds-b:2007";
	public static final String XACML_2_PREFIX = "xacml2";
	public static final String XACML_2_URI = "urn:oasis:names:tc:xacml:2.0:policy:schema:os";
	
	private static PepNamespaceContext ctx;

	@BeforeClass
	public static void setUp() throws Exception {
		ctx = new PepNamespaceContext();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		ctx = null;
	}

	@Test
	public void testGetNamespaceURI() {
		assertEquals(HL7_URI,ctx.getNamespaceURI(HL7_PREFIX));
		assertEquals(XENC_URI,ctx.getNamespaceURI(XENC_PREFIX));
		assertEquals(RIM_URI,ctx.getNamespaceURI(RIM_PREFIX));
		assertEquals(QUERY_URI,ctx.getNamespaceURI(QUERY_PREFIX));
		assertEquals(IHE_ITI_XDSB_2007_URI,ctx.getNamespaceURI(IHE_ITI_XDSB_2007_PREFIX));
		assertEquals(XACML_2_URI,ctx.getNamespaceURI(XACML_2_PREFIX));
		assertNull(ctx.getNamespaceURI("NOT_A_URI"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetPrefixes() {
		ctx.getPrefixes("someString");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetPrefix() {
		ctx.getPrefix("someString");
	}
}
