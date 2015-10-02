package gov.samhsa.acs.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import ch.qos.logback.audit.Application;
import ch.qos.logback.audit.AuditException;
import ch.qos.logback.audit.client.AuditorFacade;
import ch.qos.logback.audit.client.AuditorFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuditorFactory.class)
public class AuditServiceImplStaticTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private String applicationNameMock = "applicationNameMock";

	@InjectMocks
	private AuditServiceImpl sut;

	@Test
	public void testCreatePredicateMap() {
		// Act
		Map<PredicateKey, String> actualResponse = sut.createPredicateMap();

		// Assert
		assertNotNull(actualResponse);
		assertTrue(actualResponse instanceof Map);
		assertEquals(0, actualResponse.size());
	}

	@Test
	public void testGetApplicationName() {
		// Arrange
		ReflectionTestUtils.setField(sut, "applicationName",
				applicationNameMock);

		// Act
		String actualResponse = sut.getApplicationName();

		// Assert
		assertEquals(applicationNameMock, actualResponse);
	}

	@Test
	public void testInit() throws AuditException {
		// Arrange
		PowerMockito.mockStatic(AuditorFactory.class);
		PowerMockito.doNothing().when(AuditorFactory.class);
		AuditorFactory.setApplicationName(anyString());
		
		// Act
		sut.init();
		
		// Assert
		PowerMockito.verifyStatic(times(1));
		AuditorFactory.setApplicationName(anyString());		
	}

	@Test
	public void testDestroy() {
		// Arrange
		PowerMockito.mockStatic(AuditorFactory.class);
		PowerMockito.doNothing().when(AuditorFactory.class);
		AuditorFactory.reset();
		
		// Act
		sut.destroy();
		
		// Assert
		PowerMockito.verifyStatic(times(1));
		AuditorFactory.reset();
	}

	@Test
	public void testCreateApplication() {
		// Arrange
		String hostAddressMock = "hostAddressMock";

		// Act
		Application actualResponse = sut.createApplication(this,
				hostAddressMock);

		// Assert
		assertEquals(hostAddressMock, actualResponse.getIpAddress());
		assertEquals(this.getClass().getName(), actualResponse.getName());
	}

	@Test
	public void testCreateAuditorFacade() {
		// Arrange
		String subjectMock = "subjectMock";
		AuditVerb auditVerbMock = AcsAuditVerb.DEPLOY_POLICY;
		String verbMock = auditVerbMock.getAuditVerb();
		String objectMock = "objectMock";

		// Act
		AuditorFacade actualResponse = sut.createAuditorFacade(subjectMock,
				auditVerbMock, objectMock);

		// Assert
		assertEquals(subjectMock,
				ReflectionTestUtils.getField(actualResponse, "subject"));
		assertEquals(verbMock,
				ReflectionTestUtils.getField(actualResponse, "verb"));
		assertEquals(objectMock,
				ReflectionTestUtils.getField(actualResponse, "object"));
	}
}
