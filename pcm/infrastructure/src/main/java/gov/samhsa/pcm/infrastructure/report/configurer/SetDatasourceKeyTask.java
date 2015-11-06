package gov.samhsa.pcm.infrastructure.report.configurer;

import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

/**
 * This task returns {@link ReportProps#getDatasourceKey()} parameter with
 * {@link JRDataSource} value.<br>
 * <br>
 * In other words, this tasks adds the {@link JRDataSource} to the report
 * model/parameters.
 */
public class SetDatasourceKeyTask implements ReportParameterConfigurerTask {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask
	 * #configure(gov.samhsa.consent2share.infrastructure.report.ReportProps,
	 * gov.samhsa.consent2share.infrastructure.report.ReportFormat,
	 * net.sf.jasperreports.engine.JRDataSource)
	 */
	@Override
	public Map<String, Object> configure(ReportProps reportProps,
			ReportFormat reportFormat, JRDataSource datasource) {
		final Map<String, Object> parameters = newMap();
		// Add datasource
		parameters.put(reportProps.getDatasourceKey(), datasource);
		return parameters;
	}

}
