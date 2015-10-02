package gov.samhsa.consent2share.infrastructure.report.configurer;

import gov.samhsa.consent2share.infrastructure.report.ReportFormat;
import gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.consent2share.infrastructure.report.ReportProps;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParameters;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParametersProvider;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.util.Assert;

/**
 * This task depends on {@link RequestScopedParametersProvider} to get
 * {@link RequestScopedParameters} instance and returns
 * {@link RequestScopedParameters#parameters} as it is after asserting that it
 * is not empty.
 */
public class SetRequestScopedParametersTask implements
		ReportParameterConfigurerTask {

	/** The request scoped parameters provider. */
	private final RequestScopedParametersProvider requestScopedParametersProvider;

	/**
	 * Instantiates a new sets the request scoped parameters task.
	 *
	 * @param requestScopedParametersProvider
	 *            the request scoped parameters provider
	 */
	public SetRequestScopedParametersTask(
			RequestScopedParametersProvider requestScopedParametersProvider) {
		super();
		this.requestScopedParametersProvider = requestScopedParametersProvider;
	}

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
		final Map<String, Object> parameters = this.requestScopedParametersProvider
				.getRequestScopedParameters().getParameters();
		Assert.notEmpty(parameters,
				"RequestScopedParameters.parameters must have at least '1' element");
		return parameters;
	}
}
