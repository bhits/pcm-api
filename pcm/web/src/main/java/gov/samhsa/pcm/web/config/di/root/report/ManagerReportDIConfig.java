package gov.samhsa.pcm.web.config.di.root.report;

import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.JdbcTemplateReportDataProvider;
import gov.samhsa.pcm.infrastructure.report.ReportDIConfig;
import gov.samhsa.pcm.infrastructure.report.ReportDataProvider;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerChainBuilder;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;
import gov.samhsa.pcm.infrastructure.report.ReportViewFactory;
import gov.samhsa.pcm.infrastructure.report.SqlScriptProvider;
import gov.samhsa.pcm.infrastructure.report.configurer.OnlyPaginatePdfTask;
import gov.samhsa.pcm.infrastructure.report.configurer.SetDatasourceKeyTask;
import gov.samhsa.pcm.infrastructure.report.configurer.SetExportFormatTask;
import gov.samhsa.pcm.infrastructure.report.configurer.SetImageMappingsTask;
import gov.samhsa.pcm.infrastructure.report.configurer.SetRequestScopedParametersTask;
import gov.samhsa.pcm.service.report.ClasspathSqlScriptProvider;
import gov.samhsa.pcm.service.report.ManagerReportRowMapper;
import gov.samhsa.pcm.web.config.report.ManagerReportConfig;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

/**
 * {@link ReportDIConfig} implementation for ManagerReport.
 */
@Configuration
public class ManagerReportDIConfig implements ReportDIConfig {

	/** The only paginate pdf task. */
	@Autowired
	private OnlyPaginatePdfTask onlyPaginatePdfTask;

	/** The set datasource key task. */
	@Autowired
	private SetDatasourceKeyTask setDatasourceKeyTask;

	/** The set export format task. */
	@Autowired
	private SetExportFormatTask setExportFormatTask;
	
	/** The set request scoped parameters task. */
	@Autowired
	private SetRequestScopedParametersTask setRequestScopedParametersTask;

	/** The servlet context. */
	@Autowired
	private ServletContext servletContext;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#reportConfig
	 * ()
	 */
	@Override
	@Bean(name = ManagerReportConfig.REPORT_CONFIG_NAME)
	public AbstractReportConfig reportConfig() {
		return new ManagerReportConfig(servletContext,
				reportParameterConfigurerChain());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#
	 * reportDataProvider(org.springframework.jdbc.core.JdbcTemplate)
	 */
	@Override
	@Bean(name = ManagerReportConfig.REPORT_DATA_PROVIDER_NAME)
	public ReportDataProvider reportDataProvider(JdbcTemplate jdbcTemplate) {
		return new JdbcTemplateReportDataProvider(jdbcTemplate,
				sqlScriptProvider(), Optional.of(rowMapper()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#
	 * reportParameterConfigurerChain()
	 */
	@Override
	@Bean
	public List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain() {
		return ReportParameterConfigurerChainBuilder
				.add(this::getOnlyPaginatePdfTask)
				.add(this::getSetDatasourceKeyTask)
				.add(this::getSetExportFormatTask)
				.add(this::getSetRequestScopedParametersTask)
				.add(() -> SetImageMappingsTask.newInstance(reportConfig()))
				.build();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#reportView
	 * ()
	 */
	@Override
	@Bean(name = ManagerReportConfig.REPORT_NAME)
	public JasperReportsMultiFormatView reportView() {
		return ReportViewFactory.newJasperReportsMultiFormatView(reportConfig()
				.getReportProps());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#rowMapper()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@Bean
	public RowMapper rowMapper() {
		return new ManagerReportRowMapper();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.ReportDIConfig#
	 * sqlScriptProvider()
	 */
	@Override
	@Bean
	public SqlScriptProvider sqlScriptProvider() {
		return new ClasspathSqlScriptProvider(reportConfig());
	}

	/**
	 * Gets the only paginate pdf task.
	 *
	 * @return the only paginate pdf task
	 */
	private OnlyPaginatePdfTask getOnlyPaginatePdfTask() {
		return onlyPaginatePdfTask;
	}

	/**
	 * Gets the set the datasource key task.
	 *
	 * @return the set the datasource key task
	 */
	private SetDatasourceKeyTask getSetDatasourceKeyTask() {
		return setDatasourceKeyTask;
	}

	/**
	 * Gets the set the export format task.
	 *
	 * @return the set the export format task
	 */
	private SetExportFormatTask getSetExportFormatTask() {
		return setExportFormatTask;
	}

	public SetRequestScopedParametersTask getSetRequestScopedParametersTask() {
		return setRequestScopedParametersTask;
	}
	
	

}
