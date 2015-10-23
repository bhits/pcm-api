package gov.samhsa.pcm.infrastructure.report.configurer;

import static org.junit.Assert.assertEquals;
import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.Map;

import gov.samhsa.pcm.infrastructure.report.configurer.SetExportFormatTask;
import net.sf.jasperreports.engine.JRDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

@RunWith(MockitoJUnitRunner.class)
public class SetExportFormatTaskTest {
	@Mock
	private ReportProps reportProps;

	@Mock
	private JRDataSource datasource;

	private SetExportFormatTask sut;

	@Before
	public void setUp() throws Exception {
		sut = new SetExportFormatTask();
	}

	@Test
	public void testConfigure_CSV() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.CSV;

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(1, configure.size());
		assertEquals(reportFormat.getFormat(),
				configure.get(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY));
	}

	@Test
	public void testConfigure_HTML() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.HTML;

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(1, configure.size());
		assertEquals(reportFormat.getFormat(),
				configure.get(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY));
	}

	@Test
	public void testConfigure_PDF() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.PDF;

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(1, configure.size());
		assertEquals(reportFormat.getFormat(),
				configure.get(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY));
	}

	@Test
	public void testConfigure_XLS() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.XLS;

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(1, configure.size());
		assertEquals(reportFormat.getFormat(),
				configure.get(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY));
	}

}
