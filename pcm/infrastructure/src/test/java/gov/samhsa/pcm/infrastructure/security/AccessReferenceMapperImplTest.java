package gov.samhsa.pcm.infrastructure.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import gov.samhsa.pcm.infrastructure.security.AccessReferenceMapperImpl;
import gov.samhsa.pcm.infrastructure.security.HasId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.owasp.esapi.AccessReferenceMap;
import org.owasp.esapi.errors.AccessControlException;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccessReferenceMapperImplTest {

	AccessReferenceMapperImpl sut;
	HttpSession session;
	final String INDIRECT_REFERENCE = "ScrambledText";
	final Long DIRECT_REFERENCE_LONG = (long) 1;
	final String DIRECT_REFERENCE_STRING = "1";
	final String MAP_NAME = "AccessReferenceMap";

	@Before
	public void setUp() throws AccessControlException {
		AccessReferenceMapperImpl accessReferenceMapper = new AccessReferenceMapperImpl();
		sut = spy(accessReferenceMapper);
		session = mock(HttpSession.class);
		@SuppressWarnings("unchecked")
		AccessReferenceMap<String> map = (AccessReferenceMap<String>) mock(AccessReferenceMap.class);
		Object directReference = String.valueOf(DIRECT_REFERENCE_STRING);

		doReturn(map).when(sut).getMap();
		doReturn(INDIRECT_REFERENCE).when(map).addDirectReference(anyString());
		doReturn(INDIRECT_REFERENCE).when(map).addDirectReference(anyLong());
		doReturn(directReference).when(map).getDirectReference(
				INDIRECT_REFERENCE);
		doReturn(session).when(sut).getSession();
	}

	@Test
	public void testGetDirectReference() {
		assertEquals(DIRECT_REFERENCE_LONG,
				Long.valueOf(sut.getDirectReference(INDIRECT_REFERENCE)));
	}

	@Test
	public void testGetIndirectReference() {
		assertEquals(INDIRECT_REFERENCE,
				sut.getIndirectReference(DIRECT_REFERENCE_STRING));
	}

	@Test
	public void testSetupAccessReferenceMap() {
		Set<HasId> objectSet = new HashSet<HasId>();
		HasId hasId1 = mock(HasId.class);
		HasId hasId2 = mock(HasId.class);
		objectSet.add(hasId1);
		objectSet.add(hasId2);
		doReturn("1").when(hasId1).getId();
		doReturn("2").when(hasId2).getId();

		sut.setupAccessReferenceMap(objectSet);
		verify(hasId1).setId(INDIRECT_REFERENCE);
		verify(hasId2).setId(INDIRECT_REFERENCE);
	}

}
