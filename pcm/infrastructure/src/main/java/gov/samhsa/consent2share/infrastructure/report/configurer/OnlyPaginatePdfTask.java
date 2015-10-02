package gov.samhsa.consent2share.infrastructure.report.configurer;

import static gov.samhsa.consent2share.infrastructure.report.ReportFormat.PDF;
import gov.samhsa.consent2share.infrastructure.report.ReportFormat;
import gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.consent2share.infrastructure.report.ReportProps;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;

/**
 * This task returns {@link JRParameter#IS_IGNORE_PAGINATION} parameter with
 * {@link Boolean#TRUE} value if the report format is <b>NOT</b>
 * {@link ReportFormat#PDF}. Otherwise, it returns an empty
 * {@code Map<String, Object>}. The default value for pagination is
 * {@link Boolean#FALSE}.<br>
 * <br>
 * In other words, this task instructs JasperReports to paginate only for PDF
 * output format.
 */
public class OnlyPaginatePdfTask implements ReportParameterConfigurerTask {

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
		if (!reportFormat.equals(PDF)) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		}
		return parameters;
	}

}
