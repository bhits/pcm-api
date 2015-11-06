package gov.samhsa.pcm.service.report;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.pcm.infrastructure.report.ReportProps;
import gov.samhsa.pcm.service.report.ClasspathSqlScriptProvider;
import gov.samhsa.pcm.service.report.exception.SqlScriptFileException;

import java.util.List;
import java.util.function.Supplier;

import javax.servlet.ServletContext;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class ClasspathSqlScriptProviderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private TestReportConfig reportConfig;

	private ClasspathSqlScriptProvider sut;

	@Test
	public void testGetSqlScript() {
		// Arrange
		final ReportProps reportProps = mock(ReportProps.class);
		final String sqlScriptFileLocation = "report/sql/testReport.sql";
		when(reportProps.getSqlScriptFileLocation()).thenReturn(
				sqlScriptFileLocation);
		when(reportConfig.getReportProps()).thenReturn(reportProps);
		sut = new ClasspathSqlScriptProvider(reportConfig);

		// Act
		final String sqlScript = sut.getSqlScript();

		// Assert
		assertTrue(StringUtils.hasText(sqlScript));
	}

	@Test
	public void testGetSqlScript_Throws_SqlScriptFileException() {
		// Arrange
		thrown.expect(SqlScriptFileException.class);
		final ReportProps reportProps = mock(ReportProps.class);
		final String sqlScriptFileLocation = "report/sql/invalidFile.sql";
		when(reportProps.getSqlScriptFileLocation()).thenReturn(
				sqlScriptFileLocation);
		when(reportConfig.getReportProps()).thenReturn(reportProps);
		sut = new ClasspathSqlScriptProvider(reportConfig);

		// Act
		final String sqlScript = sut.getSqlScript();

		// Assert
		assertTrue(StringUtils.hasText(sqlScript));
	}

	public class TestReportConfig extends AbstractReportConfig {

		public TestReportConfig(
				ServletContext servletContext,
				List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain) {
			super(servletContext, reportParameterConfigurerChain);
		}

		@Override
		public String getReportConfigName() {
			return null;
		}

		@Override
		public String getReportDataProviderName() {
			return null;
		}

		@Override
		public String getReportName() {
			return null;
		}
	}

}
