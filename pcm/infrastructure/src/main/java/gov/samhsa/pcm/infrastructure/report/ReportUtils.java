package gov.samhsa.pcm.infrastructure.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The Interface ReportUtils.
 */
public interface ReportUtils {
	
	/**
	 * Gets the epoch start date time.
	 *
	 * @param localDate the local date
	 * @return the epoch start date time
	 */
	public abstract LocalDateTime getStartDateTime(LocalDate localDate);

	/**
	 * Gets the epoch end date time.
	 *
	 * @param localDate the local date
	 * @return the epoch end date time
	 */
	public abstract LocalDateTime getEndDateTime(LocalDate localDate);

	/**
	 * Convert local date time to epoch.
	 *
	 * @param localDateTime the local date time
	 * @return the long
	 */
	public abstract long convertLocalDateTimeToEpoch(LocalDateTime localDateTime);

}