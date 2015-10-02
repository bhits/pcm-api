package gov.samhsa.consent2share.infrastructure.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportTypeConvertTest {
	
	@InjectMocks
	private ReportTypeConvert reportTypeConvert;
	
	@Test
	public void testGetEndDateTime(){
		LocalDate localDate = LocalDate.of(1970, 01, 01);
		LocalDateTime expectDateTime = LocalDateTime.of(1970, 01, 01, 23, 59, 59,999999999);
		LocalDateTime resultDateTime = reportTypeConvert.getEndDateTime(localDate);
		
		Assert.assertEquals(expectDateTime, resultDateTime);
	}
	
	@Test
	public void testConvertLocalDateTimeToEpoch() {
		LocalDateTime localDateTime = LocalDateTime.of(1970, 01, 01, 00, 00, 00);
		long expectEpoch = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toEpochSecond()*1000;
		
		long resultEpoch = reportTypeConvert.convertLocalDateTimeToEpoch(localDateTime);
		
		Assert.assertEquals(expectEpoch,resultEpoch);
	}
}
