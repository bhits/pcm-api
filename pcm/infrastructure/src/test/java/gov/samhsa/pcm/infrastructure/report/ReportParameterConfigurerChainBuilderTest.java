package gov.samhsa.pcm.infrastructure.report;

import static org.junit.Assert.assertEquals;

import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerChainBuilder;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerChainBuilder.ReportParameterConfigurerTaskAdder;

import java.util.HashMap;
import java.util.Map;

import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportParameterConfigurerChainBuilderTest {

	private static final String key = "key";

	private static final Object value = "value";

	private final Map<String, Object> map = new HashMap<>();

	private ReportParameterConfigurerTask task;

	@Test
	public void testAdd() {
		// Arrange
		map.put(key, value);
		task = (reportProps, reportFormat, datasource) -> map;

		// Act
		final ReportParameterConfigurerTaskAdder add = ReportParameterConfigurerChainBuilder
				.add(() -> task);

		// Assert
		assertEquals(map, add.build().get(0).get().configure(null, null, null));
	}

}
