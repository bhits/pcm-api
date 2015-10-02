package gov.samhsa.consent2share.infrastructure.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

@RunWith(MockitoJUnitRunner.class)
public class ReportViewFactoryTest {

	@Test
	public void testNewJasperReportsMultiFormatView() {
		// Arrange
		final String name = "name";
		final String templateLocationValue = "templateLocation";
		final String templateUrl = templateLocationValue + name
				+ ReportProps.REPORT_TEMPLATE_FORMAT;
		final Optional<String> templateLocation = Optional
				.of(templateLocationValue);
		final String datasourceKeyValue = "datasourceKey";
		final Optional<String> datasourceKey = Optional.of(datasourceKeyValue);
		final Map<String, String> imageMappingValue = new HashMap<>();
		final String key = "key";
		final String value = "value";
		imageMappingValue.put(key, value);
		final Optional<Map<String, String>> imageMapping = Optional
				.of(imageMappingValue);
		final String baseClasspathSqlScriptResourcesValue = "baseClasspathSqlScriptResourcesValue";
		final Optional<String> baseClasspathSqlScriptResources = Optional
				.of(baseClasspathSqlScriptResourcesValue);
		final String sqlScriptFileNameValue = "sqlScriptFileNameValue";
		final Optional<String> sqlScriptFileName = Optional
				.of(sqlScriptFileNameValue);
		final ReportProps reportProps = new ReportProps(name, templateLocation,
				datasourceKey, imageMapping, baseClasspathSqlScriptResources,
				sqlScriptFileName);

		// Act
		final JasperReportsMultiFormatView view = ReportViewFactory
				.newJasperReportsMultiFormatView(reportProps);

		// Assert
		assertNotNull(view);
		assertEquals(templateUrl, view.getUrl());
		assertEquals(datasourceKeyValue,
				ReflectionTestUtils.getField(view, "reportDataKey"));
	}
}
