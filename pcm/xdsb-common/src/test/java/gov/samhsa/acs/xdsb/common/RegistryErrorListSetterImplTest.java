package gov.samhsa.acs.xdsb.common;

import static org.junit.Assert.assertEquals;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;

import org.junit.Before;
import org.junit.Test;

public class RegistryErrorListSetterImplTest {
	private static final String URN_PARTIAL_SUCCESS = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:PartialSuccess";
	private static final String URN_FAILURE = "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure";
	
	private RegistryErrorListSetterImpl sut;
	
	@Before
	public void setUp() throws Exception {
		sut = new RegistryErrorListSetterImpl();
	}

	@Test
	public void testSetRegistryErrorList_PartialSuccess() {
		// Arrange
		RetrieveDocumentSetResponse retrieveDocumentSetResponseMock = new RetrieveDocumentSetResponse();
		RegistryErrorList registryErrorListMock = new RegistryErrorList();
		boolean isPartial = true;
		
		// Act
		sut.setRegistryErrorList(retrieveDocumentSetResponseMock, registryErrorListMock, isPartial);
		
		// Assert
		assertEquals(registryErrorListMock, retrieveDocumentSetResponseMock.getRegistryResponse().getRegistryErrorList());
		assertEquals(URN_PARTIAL_SUCCESS, retrieveDocumentSetResponseMock.getRegistryResponse().getStatus());
	}
	
	@Test
	public void testSetRegistryErrorList_Failure() {
		// Arrange
		RetrieveDocumentSetResponse retrieveDocumentSetResponseMock = new RetrieveDocumentSetResponse();
		RegistryErrorList registryErrorListMock = new RegistryErrorList();
		boolean isPartial = false;
		
		// Act
		sut.setRegistryErrorList(retrieveDocumentSetResponseMock, registryErrorListMock, isPartial);
		
		// Assert
		assertEquals(registryErrorListMock, retrieveDocumentSetResponseMock.getRegistryResponse().getRegistryErrorList());
		assertEquals(URN_FAILURE, retrieveDocumentSetResponseMock.getRegistryResponse().getStatus());
	}
}
