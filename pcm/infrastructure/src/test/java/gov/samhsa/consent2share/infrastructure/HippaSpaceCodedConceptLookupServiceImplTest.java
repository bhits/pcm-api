package gov.samhsa.consent2share.infrastructure;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HippaSpaceCodedConceptLookupServiceImplTest {
	
	@InjectMocks
	HippaSpaceCodedConceptLookupServiceImpl hs;
	
	@Value("${HIPAASpaceRestfulWebService}") 
	String HIPAASpaceURL;
	@Value("${HIPAASpaceRestfulWebServiceToken}")
	String securityToken;
	
	@Mock
	private RestfulQueryProxy queryProxy;

	@Before
	public void setUp() throws Exception {
		
		System.out.println(queryProxy);
		when(queryProxy.call(anyString(), anyString())).thenReturn("hahaha");
	}

	@Test
	public void testSearchCodes( ) {
		hs.searchCodes("domain", "resultType", "query");
		verify(queryProxy).call(HIPAASpaceURL, "domain/search?q=query&rt=resultType&token="+securityToken);
	}

}
