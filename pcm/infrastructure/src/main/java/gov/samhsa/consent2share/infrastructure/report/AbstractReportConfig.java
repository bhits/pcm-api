package gov.samhsa.consent2share.infrastructure.report;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * This is the base abstract class for a concrete report configuration
 * implementation. The abstract methods in this class require the minimum
 * implementation to apply auto-configuration for reports. This
 * auto-configuration is useful if a certain convention is followed as explained
 * in the project README.md documentation. If the convention is not fully
 * followed, the relevant methods in this class can be overridden for
 * customization.
 */
public abstract class AbstractReportConfig {

	/**
	 * The default webpath location for image resources that can be resolved for
	 * a report. This path is meaningful if the report is rendered as HTML.
	 */
	public static final String DEFAULT_BASE_WEBPATH_IMG_RESOURCES = "/resources/report/img/";

	/**
	 * The default classpath location for image resources that can be resolved
	 * for a report. This path is meaningful if the report is rendered as a
	 * binary file such as PDF or XLS.
	 */
	public static final String DEFAULT_BASE_CLASSPATH_IMG_RESOURCES = "../.."
			+ DEFAULT_BASE_WEBPATH_IMG_RESOURCES;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * This is the chain of report parameter configurer tasks that will be run
	 * by report controllers to configure the report model/parameters. This
	 * model will eventually used by report views to fill the report and display
	 * it.
	 */
	private final List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain;

	/**
	 * The servlet context. The servlet context is only needed to get the
	 * context path for image webpath resolutions.
	 */
	private final ServletContext servletContext;

	/**
	 * The report props contain the basic report properties including the report
	 * name, template URL and datasource key in the report model/parameters.
	 */
	private ReportProps reportProps;

	/**
	 * The report image resolver. This is an optional field and only required if
	 * there are any images in the report that need to be resolved.
	 */
	private Optional<ReportImageResolver> reportImageResolver;

	/**
	 * Instantiates a new abstract report config.
	 *
	 * @param servletContext
	 *            the servlet context
	 * @param reportParameterConfigurerChain
	 *            the report parameter configurer chain
	 */
	public AbstractReportConfig(
			ServletContext servletContext,
			List<Supplier<ReportParameterConfigurerTask>> reportParameterConfigurerChain) {
		super();
		Assert.notEmpty(reportParameterConfigurerChain,
				"reportParameterConfigurerChain cannot be empty");
		this.servletContext = servletContext;
		this.reportImageResolver = Optional.empty();
		this.reportParameterConfigurerChain = reportParameterConfigurerChain;
	}

	/**
	 * Initialization callback after properties are set.
	 */
	@PostConstruct
	public final void afterPropertiesSet() {
		final Optional<Map<String, String>> imageMapping = Optional
				.ofNullable(imageMapping());
		this.reportProps = new ReportProps(getReportName(), getTemplateUrl(),
				getDatasourceKey(), imageMapping,
				getBaseClasspathForSqlScriptResources(), getSqlScriptFileName());
		imageMapping.ifPresent(mapping -> this.reportImageResolver = Optional
				.of(new ReportImageResolverImpl(getContextPath(),
						getBaseWebpathForImgResources(),
						getBaseClasspathForImgResources(), mapping)));
	}

	/**
	 * Runs all report parameter configurer tasks in the chain, builds and
	 * returns the report model/parameters as the result.
	 *
	 * @param reportFormat
	 *            the report format
	 * @param datasource
	 *            the datasource
	 * @return the map (report model/parameters)
	 */
	public Map<String, Object> configure(ReportFormat reportFormat,
			JRDataSource datasource) {
		final Map<String, Object> parameters = this.reportParameterConfigurerChain
				.stream()
				.map(Supplier::get)
				.peek(task -> logger
						.debug("Running report parameter configurer task:"
								+ task.getClass().getName()))
				.map(task -> task.configure(getReportProps(), reportFormat,
						datasource))
				.flatMap(map -> map.entrySet().stream())
				.collect(
						toMap(entry -> entry.getKey(),
								entry -> entry.getValue()));
		return parameters;
	}

	/**
	 * Gets the base classpath for img resources. Can be overridden for
	 * customization.
	 *
	 * @return the base classpath for img resources
	 */
	public String getBaseClasspathForImgResources() {
		return DEFAULT_BASE_CLASSPATH_IMG_RESOURCES;
	}

	/**
	 * Gets the base classpath for sql script resources. Can be overridden for
	 * customization.
	 *
	 * @return the base classpath for sql script resources
	 */
	public Optional<String> getBaseClasspathForSqlScriptResources() {
		return Optional.empty();
	}

	/**
	 * Gets the base webpath for img resources. Can be overridden for
	 * customization.
	 *
	 * @return the base webpath for img resources
	 */
	public String getBaseWebpathForImgResources() {
		return DEFAULT_BASE_WEBPATH_IMG_RESOURCES;
	}

	/**
	 * Gets the context path.
	 *
	 * @return the context path
	 */
	public String getContextPath() {
		Assert.notNull(this.servletContext, "servletContext cannot be null");
		return this.servletContext.getContextPath();
	}

	/**
	 * Gets the datasource key. Can be overridden for customization.
	 *
	 * @return the datasource key
	 */
	public Optional<String> getDatasourceKey() {
		return Optional.empty();
	}

	/**
	 * Gets the report config name.
	 *
	 * @return the report config name
	 */
	public abstract String getReportConfigName();

	/**
	 * Gets the report data provider name.
	 *
	 * @return the report data provider name
	 */
	public abstract String getReportDataProviderName();

	/**
	 * Gets the report image resolver.
	 *
	 * @return the report image resolver
	 */
	public Optional<ReportImageResolver> getReportImageResolver() {
		return reportImageResolver;
	}

	/**
	 * Gets the report name. This report name must be globally unique across all
	 * reports.
	 *
	 * @return the report name
	 */
	public abstract String getReportName();

	/**
	 * Gets the report props.
	 *
	 * @return the report props
	 */
	public ReportProps getReportProps() {
		return reportProps;
	}

	/**
	 * Gets the sql script file name. Can be overridden for customization.
	 *
	 * @return the sql script file name
	 */
	public Optional<String> getSqlScriptFileName() {
		return Optional.empty();
	}

	/**
	 * Gets the template url. Can be overridden for customization.
	 *
	 * @return the template url
	 */
	public Optional<String> getTemplateUrl() {
		return Optional.empty();
	}

	/**
	 * Image mapping for this report. Needs to be overridden in the concrete
	 * class if there are any images in the report.
	 *
	 * @return the image mapping for this report
	 */
	public Map<String, String> imageMapping() {
		return null;
	}

	/**
	 * This method can be used to consolidate several image
	 * {@link #mapping(String, String)}s as a single map. These mappings should
	 * be from the image's parameter name in the JRXML template to the file name
	 * that can be resolved by the {@link #reportImageResolver}.
	 *
	 * @param imageMappings
	 *            the image mappings
	 * @return the map (consolidated image mappings)
	 */
	@SafeVarargs
	protected static final Map<String, String> imageMappings(
			Map<String, String>... imageMappings) {
		return Arrays
				.stream(imageMappings)
				.flatMap(mappings -> mappings.entrySet().stream())
				.collect(
						toMap(entry -> entry.getKey(),
								entry -> entry.getValue()));
	}

	/**
	 * Creates a single mapping from image's parameter name in the JRXML
	 * template to the file name that can be resolved by the report image.
	 *
	 * @param paramInReportTemplate
	 *            the parameter name in report template (JRXML file)
	 * @param actualFileName
	 *            the actual file name that can be resolved by the
	 *            {@link #reportImageResolver}
	 * @return the map (a single image mapping)
	 */
	protected static final Map<String, String> mapping(
			String paramInReportTemplate, String actualFileName) {
		Assert.hasText(paramInReportTemplate);
		Assert.hasText(actualFileName);
		final Map<String, String> map = new HashMap<>();
		map.put(paramInReportTemplate, actualFileName);
		return map;
	}

}
