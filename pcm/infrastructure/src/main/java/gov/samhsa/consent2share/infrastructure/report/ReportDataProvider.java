package gov.samhsa.consent2share.infrastructure.report;

import java.util.Collection;

/**
 * An interface that provides the report data. {@link AbstractReportController}
 * depends on this interface to get the report data.
 *
 * @see AbstractReportController
 */
public interface ReportDataProvider {

	/**
	 * Gets the report data. This method will be called by report controllers to
	 * get the data for the report.
	 *
	 * @param <T>
	 *            the generic type
	 * @param args
	 *            the args that can be passed down to the implementation to
	 *            parameterize the query
	 * @return the report data that can be filled to the report
	 */
	public abstract <T> Collection<T> getReportData(Object... args);

}
