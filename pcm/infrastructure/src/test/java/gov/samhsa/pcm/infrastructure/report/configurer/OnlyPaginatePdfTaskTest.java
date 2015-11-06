package gov.samhsa.pcm.infrastructure.report.configurer;

import static org.junit.Assert.assertEquals;
import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.Map;

import gov.samhsa.pcm.infrastructure.report.configurer.OnlyPaginatePdfTask;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OnlyPaginatePdfTaskTest {

	@Mock
	private ReportProps reportProps;

	@Mock
	private JRDataSource datasource;

	private OnlyPaginatePdfTask sut;

	@Before
	public void setUp() throws Exception {
		sut = new OnlyPaginatePdfTask();
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
		assertEquals(configure.get(JRParameter.IS_IGNORE_PAGINATION),
				Boolean.TRUE);
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
		assertEquals(configure.get(JRParameter.IS_IGNORE_PAGINATION),
				Boolean.TRUE);
	}

	@Test
	public void testConfigure_PDF() {
		// Arrange
		final ReportFormat reportFormat = ReportFormat.PDF;

		// Act
		final Map<String, Object> configure = sut.configure(reportProps,
				reportFormat, datasource);

		// Assert
		assertEquals(0, configure.size());
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
		assertEquals(configure.get(JRParameter.IS_IGNORE_PAGINATION),
				Boolean.TRUE);
	}

}
