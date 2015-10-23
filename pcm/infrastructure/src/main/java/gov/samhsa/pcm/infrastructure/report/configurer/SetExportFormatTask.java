package gov.samhsa.pcm.infrastructure.report.configurer;

import gov.samhsa.pcm.infrastructure.report.ReportFormat;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.pcm.infrastructure.report.ReportProps;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * This task returns {@link JasperReportsMultiFormatView#DEFAULT_FORMAT_KEY}
 * parameter with {@link ReportFormat#getFormat()} value.<br>
 * <br>
 * In other words, this task adds the output format that is required by
 * {@link JasperReportsMultiFormatView}.
 */
public class SetExportFormatTask implements ReportParameterConfigurerTask {

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
		parameters.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY,
				reportFormat.getFormat());
		return parameters;
	}

}
