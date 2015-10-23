package gov.samhsa.pcm.infrastructure.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import gov.samhsa.pcm.infrastructure.report.ReportProps;
import org.junit.Before;
import org.junit.Test;

public class ReportPropsTest {

	private static final String VALUE = "value";
	private static final String KEY = "key";
	private static final String DATASOURCE_KEY = "datasourceKey";
	private static final String TEMPLATE_LOCATION = "templateLocation/";
	private static final String BASE_CLASSPATH_SQL_SCRIPT_RESOURCES = "BASE_CLASSPATH_SQL_SCRIPT_RESOURCES";
	private static final String SQL_SCRIPT_FILE_NAME = "SQL_SCRIPT_FILE_NAME";
	private static final String name = "name";
	private static final Optional<String> templateLocation = Optional
			.of(TEMPLATE_LOCATION);
	private static final Optional<String> datasourceKey = Optional
			.of(DATASOURCE_KEY);
	private static Optional<String> baseClasspathSqlScriptResources = Optional
			.of(BASE_CLASSPATH_SQL_SCRIPT_RESOURCES);
	private static final Optional<String> sqlScriptFileName = Optional
			.of(SQL_SCRIPT_FILE_NAME);
	private final Map<String, String> map = new HashMap<>();
	private final Optional<Map<String, String>> imageMapping = Optional.of(map);

	private final ReportProps sut = new ReportProps(name, templateLocation,
			datasourceKey, imageMapping, baseClasspathSqlScriptResources,
			sqlScriptFileName);

	@Before
	public void setUp() throws Exception {
		map.put(KEY, VALUE);
	}

	@Test
	public void testEqualsObject() {
		// Arrange
		final ReportProps other = new ReportProps(name, Optional.empty(),
				Optional.empty(), Optional.empty(),
				baseClasspathSqlScriptResources, sqlScriptFileName);
		final ReportProps other2 = new ReportProps(name + "others",
				Optional.empty(), Optional.empty(), Optional.empty(),
				baseClasspathSqlScriptResources, sqlScriptFileName);

		// Act
		final boolean equals1 = sut.equals(other);
		final boolean equals2 = sut.equals(other2);

		// Assert
		assertTrue(equals1);
		assertFalse(equals2);
	}

	@Test
	public void testGetDatasourceKey() {
		assertEquals(DATASOURCE_KEY, sut.getDatasourceKey());
	}

	@Test
	public void testGetImageMapping() {
		assertTrue(sut.getImageMapping().isPresent());
		assertEquals(1, sut.getImageMapping().get().size());
		assertEquals(KEY, sut.getImageMapping().get().keySet().stream()
				.findFirst().get());
		assertEquals(VALUE, sut.getImageMapping().get().get(KEY));
	}

	@Test
	public void testGetName() {
		assertEquals(name, sut.getName());
	}

	@Test
	public void testGetTemplateUrl() {
		assertTrue(sut.getTemplateUrl().startsWith(TEMPLATE_LOCATION));
		assertTrue(sut.getTemplateUrl().contains(name));
		assertTrue(sut.getTemplateUrl().endsWith(
				ReportProps.REPORT_TEMPLATE_FORMAT));
	}

	@Test
	public void testHashCode() {
		assertEquals(name.hashCode(), sut.hashCode());
	}

}
