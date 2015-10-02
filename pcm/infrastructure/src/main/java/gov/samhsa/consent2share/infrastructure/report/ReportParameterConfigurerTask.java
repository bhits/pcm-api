package gov.samhsa.consent2share.infrastructure.report;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * The Interface ReportParameterConfigurerTask. Defines a single task to create
 * parameter(s) that will be eventually added to the report model/parameters.
 *
 * @see AbstractReportConfig#reportParameterConfigurerChain
 * @see AbstractReportConfig#configure(ReportFormat, JRDataSource)
 * @see AbstractReportController#reportModelAndView(ReportFormat,
 *      java.util.function.Supplier)
 */
public interface ReportParameterConfigurerTask {

	/**
	 * Create a {@code Map<String, Object>} instance with the appropriate
	 * parameters as implemented by the task.
	 *
	 * @param reportProps
	 *            the report props
	 * @param reportFormat
	 *            the report format
	 * @param datasource
	 *            the datasource
	 * @return the parameters to be added to the report model/parameters
	 */
	public abstract Map<String, Object> configure(ReportProps reportProps,
			ReportFormat reportFormat, JRDataSource datasource);

	/**
	 * Convenience method to initialize a new empty {@link HashMap}.
	 *
	 * @return the map
	 */
	public default Map<String, Object> newMap() {
		return new HashMap<>();
	}
}
