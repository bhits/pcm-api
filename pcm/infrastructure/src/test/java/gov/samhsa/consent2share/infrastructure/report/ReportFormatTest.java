package gov.samhsa.consent2share.infrastructure.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.infrastructure.report.ReportFormat;

import java.util.Arrays;

import org.junit.Test;

public class ReportFormatTest {

	@Test
	public void testAllFormatsAreLowerCase() {
		Arrays.stream(ReportFormat.values())
		.map(ReportFormat::getFormat)
		.forEach(
				format -> assertTrue(format.equals(format.toLowerCase())));
	}

	@Test
	public void testGetFormatEqualsToString() {
		Arrays.stream(ReportFormat.values()).forEach(
				format -> assertTrue(format.toString().equals(
						format.getFormat())));
	}

	@Test
	public void testUniqueValues() {
		// Arrange & Act
		final int allFieldsCount = ReportFormat.values().length;
		final long uniqueStringFieldsCount = Arrays
				.stream(ReportFormat.values()).map(ReportFormat::getFormat)
				.map(String::toLowerCase).distinct().count();

		// Assert
		assertEquals(allFieldsCount, uniqueStringFieldsCount);
	}
}
