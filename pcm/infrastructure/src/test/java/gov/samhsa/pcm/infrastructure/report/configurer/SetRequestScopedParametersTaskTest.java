package gov.samhsa.pcm.infrastructure.report.configurer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportProps;
import gov.samhsa.pcm.infrastructure.report.RequestScopedParameters;
import gov.samhsa.pcm.infrastructure.report.RequestScopedParametersProvider;
import gov.samhsa.pcm.infrastructure.report.configurer.SetRequestScopedParametersTask;
import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SetRequestScopedParametersTaskTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private ReportProps reportProps;

	@Mock
	private JRDataSource datasource;
	
	@Mock
	private RequestScopedParametersProvider requestScopedParametersProvider;

	@InjectMocks
	private SetRequestScopedParametersTask sut;
	
	@Test
	public void testConfigure_HTML() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.HTML;
		
		RequestScopedParameters requestScopedParameters = mock(RequestScopedParameters.class);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		Map<String, Object> parameters = new HashMap<>();
		String key = "key";
		String value = "value";
		parameters.put(key, value);
		when(requestScopedParameters.getParameters()).thenReturn(parameters);
		
		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(parameters,configure);
	}
	
	@Test
	public void testConfigure_HTML_Throws_IllegalArgumentException_Parameters_Empty() {
		// Arrange
		thrown.expect(IllegalArgumentException.class);
		
		final ReportFormat reportFormat = ReportFormat.HTML;
		
		RequestScopedParameters requestScopedParameters = mock(RequestScopedParameters.class);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		
		Map<String, Object> parameters = new HashMap<>();
		when(requestScopedParameters.getParameters()).thenReturn(parameters);
		
		// Act
		sut.configure(reportProps, reportFormat, datasource);
	}
	
	@Test
	public void testConfigure_HTML_Throws_IllegalArgumentException_Parameters_Null() {
		// Arrange
		thrown.expect(IllegalArgumentException.class);
		
		final ReportFormat reportFormat = ReportFormat.HTML;
		
		RequestScopedParameters requestScopedParameters = mock(RequestScopedParameters.class);
		when(requestScopedParametersProvider.getRequestScopedParameters()).thenReturn(requestScopedParameters);
		when(requestScopedParameters.getParameters()).thenReturn(null);
		
		// Act
		sut.configure(reportProps, reportFormat, datasource);

	}
}
