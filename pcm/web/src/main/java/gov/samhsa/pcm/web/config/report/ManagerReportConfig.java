package gov.samhsa.pcm.web.config.report;

import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.ReportParameterConfigurerTask;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.ServletContext;

/**
 * {@link AbstractReportConfig} implementation for ManagerReport.
 */
public class ManagerReportConfig extends AbstractReportConfig {

	/** The Constant REPORT_NAME. */
	public static final String REPORT_NAME = "managerReport";

	/** The Constant REPORT_CONFIG_NAME. */
	public static final String REPORT_CONFIG_NAME = "managerReportConfig";

	/** The Constant REPORT_DATA_PROVIDER_NAME. */
	public static final String REPORT_DATA_PROVIDER_NAME = "managerReportDataProvider";

	// image mappings
	/** The Constant PARAM_HDEPT_LOGO_100_PNG. */
	public static final String PARAM_HDEPT_LOGO_100_PNG = "logo1";

	/** The Constant PARAM_PGC_SEALCLRRLB_100_PNG. */
	public static final String PARAM_PGC_SEALCLRRLB_100_PNG = "logo2";

	/** The Constant HDEPT_LOGO_100_PNG. */
	public static final String HDEPT_LOGO_100_PNG = "hdept_logo_100.png";

	/** The Constant PGC_SEALCLRRLB_100_PNG. */
	public static final String PGC_SEALCLRRLB_100_PNG = "pgc_sealclrrlb_100.png";

	/**
	 * Instantiates a new manager report config.
	 *
	 * @param servletContext
	 *            the servlet context
	 * @param reportParameterConfigurerChain
	 *            the report parameter configurer chain
	 */
	public ManagerReportConfig(
			ServletContext servletContext,
			List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain) {
		super(servletContext, reportParameterConfigurerChain);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig#
	 * getReportConfigName()
	 */
	@Override
	public String getReportConfigName() {
		return REPORT_CONFIG_NAME;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig#
	 * getReportDataProviderName()
	 */
	@Override
	public String getReportDataProviderName() {
		return REPORT_DATA_PROVIDER_NAME;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig#
	 * getReportName()
	 */
	@Override
	public String getReportName() {
		return REPORT_NAME;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.samhsa.consent2share.infrastructure.report.AbstractReportConfig#
	 * imageMapping()
	 */
	@Override
	public Map<String, String> imageMapping() {
		return imageMappings(
				mapping(PARAM_HDEPT_LOGO_100_PNG, HDEPT_LOGO_100_PNG),
				mapping(PARAM_PGC_SEALCLRRLB_100_PNG, PGC_SEALCLRRLB_100_PNG));
	}

}
