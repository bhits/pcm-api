package gov.samhsa.consent2share.web.config.di.root.report;

import gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig;
import gov.samhsa.consent2share.infrastructure.report.JdbcTemplateReportDataProvider;
import gov.samhsa.consent2share.infrastructure.report.ReportTypeConvert;
import gov.samhsa.consent2share.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParameters;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParametersProvider;
import gov.samhsa.consent2share.infrastructure.report.RequestScopedParametersProviderImpl;
import gov.samhsa.consent2share.infrastructure.report.configurer.OnlyPaginatePdfTask;
import gov.samhsa.consent2share.infrastructure.report.configurer.SetDatasourceKeyTask;
import gov.samhsa.consent2share.infrastructure.report.configurer.SetExportFormatTask;
import gov.samhsa.consent2share.infrastructure.report.configurer.SetRequestScopedParametersTask;
import gov.samhsa.consent2share.web.config.di.root.DataAccessConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;

/**
 * Common DI config for report beans. These beans must be thread-safe and they
 * can be shared across different reports.
 */
@Configuration
public class CommonConfig {

	/**
	 * Initialize bena for Jdbc template.
	 *
	 * @param dataAccessConfig
	 *            the data access config
	 * @return the jdbc template
	 * @see JdbcTemplateReportDataProvider
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataAccessConfig dataAccessConfig) {
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(
				dataAccessConfig.dataSource());
		return jdbcTemplate;
	}

	/**
	 * Initialize bean for only paginate pdf task.
	 *
	 * @return the only paginate pdf task
	 * @see ReportParameterConfigurerTask
	 * @see AbstractReportConfig#reportParameterConfigurerChain
	 */
	@Bean
	public OnlyPaginatePdfTask onlyPaginatePdfTask() {
		return new OnlyPaginatePdfTask();
	}

	/**
	 * Initialize bean for request scoped parameters. This bean is scoped by the
	 * <b>request</b>.
	 *
	 * @return the request scoped parameters
	 * @see Scope
	 */
	@Bean
	@Scope(WebApplicationContext.SCOPE_REQUEST)
	public RequestScopedParameters requestScopedParameters() {
		return new RequestScopedParameters();
	}

	/**
	 * Initialize bean of {@link RequestScopedParametersProvider} to provide an
	 * instance of {@link RequestScopedParameters}.
	 *
	 * @return the request scoped parameters provider
	 */
	@Bean
	public RequestScopedParametersProvider requestScopedParametersProvider() {
		return new RequestScopedParametersProviderImpl();
	}

	/**
	 * Initialize a bean to set request scoped parameters
	 * {@link RequestScopedParameters} to the report model/parameters.
	 *
	 * @return the request scoped parameters setter task
	 */
	@Bean
	public SetRequestScopedParametersTask requestScopedParametersSetterTask() {
		return new SetRequestScopedParametersTask(
				requestScopedParametersProvider());
	}

	/**
	 * Initialize bean for set the datasource key task.
	 *
	 * @return the sets the datasource key task
	 * @see ReportParameterConfigurerTask
	 * @see AbstractReportConfig#reportParameterConfigurerChain
	 */
	@Bean
	public SetDatasourceKeyTask setDatasourceKeyTask() {
		return new SetDatasourceKeyTask();
	}

	/**
	 * Initialize bean for sets the export format task.
	 *
	 * @return the sets the export format task
	 * @see ReportParameterConfigurerTask
	 * @see AbstractReportConfig#reportParameterConfigurerChain
	 */
	@Bean
	public SetExportFormatTask setExportFormatTask() {
		return new SetExportFormatTask();
	}
	
	/**
	 * Initialize bean for ReportTypeConvert.
	 *
	 * @return the report date filter
	 */
	@Bean
	public ReportTypeConvert reportTypeConvert(){
		return new ReportTypeConvert();
	}

}
