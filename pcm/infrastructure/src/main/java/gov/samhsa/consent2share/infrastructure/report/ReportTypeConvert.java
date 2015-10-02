package gov.samhsa.consent2share.infrastructure.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.util.Assert;

/**
 * The Class ReportTypeConvert.
 */
public class ReportTypeConvert implements ReportUtils{
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportUtils#getEpochStartDateTime(java.time.LocalDate)
	 */
	@Override
	public LocalDateTime getStartDateTime(LocalDate localDate) {
		
		Assert.notNull(localDate, "localDate cannot be null");
		LocalDateTime localDateTime = localDate.atStartOfDay();
		
		return localDateTime;
	}
	
	/* To get end of Day time from input
	 * If input date is today to return exactly current date time
	 * If not, then return the end of input day time
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportUtils#getEpochEndDateTime(java.time.LocalDate)
	 */
	@Override
	public LocalDateTime getEndDateTime(LocalDate localDate) {
		
		Assert.notNull(localDate, "localDate cannot be null");
		LocalDateTime endOfDayTime = localDate.plusDays(1).atStartOfDay().minusNanos(1);
		LocalDateTime endDateTime = localDate.equals(LocalDate.now()) ? LocalDateTime.now() : endOfDayTime;
		
		return endDateTime;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportUtils#convertLocalDateTimeToEpoch(java.time.LocalDateTime)
	 */
	@Override
	public long convertLocalDateTimeToEpoch(LocalDateTime localDateTime) {
		
		Assert.notNull(localDateTime, "localDateTime cannot be null");
		ZoneId zoneId = ZoneId.systemDefault();
		long epoch = localDateTime.atZone(zoneId).toInstant().toEpochMilli();
		
		return epoch;
	}
}
