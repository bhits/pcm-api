package gov.samhsa.pcm.infrastructure.report.configurer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.Map;

import gov.samhsa.pcm.infrastructure.report.configurer.SetDatasourceKeyTask;
import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SetDatasourceKeyTaskTest {

	@Mock
	private ReportProps reportProps;

	private final ReportFormat reportFormat = ReportFormat.HTML;

	@Mock
	private JRDataSource datasource;

	private SetDatasourceKeyTask sut;

	@Before
	public void setUp() throws Exception {
		sut = new SetDatasourceKeyTask();
	}

	@Test
	public void testConfigure() {
		// Arrange
		final String datasourceKey = "datasourceKey";
		when(reportProps.getDatasourceKey()).thenReturn(datasourceKey);

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(1, configure.size());
		assertEquals(datasource, configure.get(datasourceKey));
	}

}
