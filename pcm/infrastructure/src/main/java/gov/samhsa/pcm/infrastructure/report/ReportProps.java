package gov.samhsa.pcm.infrastructure.report;

import java.util.Map;
import java.util.Optional;

import org.springframework.util.Assert;

/**
 * The Class ReportProps. Keeps the fundamental report properties such as
 * {@link #name}, {@link #templateUrl}, {@link #datasourceKey} and optionally
 * {@link #imageMapping} if there will be any images in the report.
 */
public class ReportProps {

	/** The Constant DEFAULT_REPORT_TEMPLATE_LOCATION. */
	public static final String DEFAULT_REPORT_TEMPLATE_LOCATION = "classpath:report/";

	/** The Constant DEFAULT_BASE_CLASSPATH_SQL_SCRIPT_RESOURCES. */
	public static final String DEFAULT_BASE_CLASSPATH_SQL_SCRIPT_RESOURCES = "report/sql/";

	/** The Constant SQL_SCRIPT_FORMAT. */
	public static final String SQL_SCRIPT_FORMAT = ".sql";

	/** The Constant REPORT_TEMPLATE_FORMAT. */
	public static final String REPORT_TEMPLATE_FORMAT = ".jrxml";

	/** The Constant DEFAULT_DATASOURCE_KEY. */
	public static final String DEFAULT_DATASOURCE_KEY = "datasource";

	/** The name. */
	private final String name;

	/** The template url. */
	private final String templateUrl;

	/** The datasource key. */
	private final String datasourceKey;

	/** The image mapping. */
	private final Optional<Map<String, String>> imageMapping;

	/** The sql script file location. */
	private final String sqlScriptFileLocation;

	/**
	 * Instantiates a new report props.
	 *
	 * @param name
	 *            the name
	 * @param templateLocation
	 *            the template location
	 * @param datasourceKey
	 *            the datasource key
	 * @param imageMapping
	 *            the image mapping
	 * @param baseClasspathSqlScriptResources
	 *            the base classpath sql script resources
	 * @param sqlScriptFileName
	 *            the sql script file name
	 */
	public ReportProps(String name, Optional<String> templateLocation,
			Optional<String> datasourceKey,
			Optional<Map<String, String>> imageMapping,
			Optional<String> baseClasspathSqlScriptResources,
			Optional<String> sqlScriptFileName) {
		super();
		Assert.hasText(name, "Report name cannot be empty or null");
		this.name = name;
		this.templateUrl = buildDefaultTemplateUrl(this.name, templateLocation);
		this.datasourceKey = datasourceKey.orElse(DEFAULT_DATASOURCE_KEY);
		this.imageMapping = imageMapping;
		this.sqlScriptFileLocation = buildSqlScriptFileLocation(
				baseClasspathSqlScriptResources, sqlScriptFileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		final ReportProps p = (ReportProps) o;
		return this.name.equals(p.getName());
	}

	/**
	 * Gets the datasource key.
	 *
	 * @return the datasource key
	 */
	public String getDatasourceKey() {
		return datasourceKey;
	}

	/**
	 * Gets the image mapping.
	 *
	 * @return the image mapping
	 */
	public Optional<Map<String, String>> getImageMapping() {
		return imageMapping;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the sql script file location.
	 *
	 * @return the sql script file location
	 */
	public String getSqlScriptFileLocation() {
		return sqlScriptFileLocation;
	}

	/**
	 * Gets the template url.
	 *
	 * @return the template url
	 */
	public String getTemplateUrl() {
		return templateUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * Builds the sql script file location.
	 *
	 * @param baseClasspathSqlScriptResources
	 *            the base classpath sql script resources
	 * @param sqlScriptFileName
	 *            the sql script file name
	 * @return the string
	 */
	private String buildSqlScriptFileLocation(
			Optional<String> baseClasspathSqlScriptResources,
			Optional<String> sqlScriptFileName) {
		return new StringBuilder()
				.append(baseClasspathSqlScriptResources
						.orElse(DEFAULT_BASE_CLASSPATH_SQL_SCRIPT_RESOURCES))
				.append(sqlScriptFileName.orElse(this.name))
				.append(SQL_SCRIPT_FORMAT).toString();
	}

	/**
	 * Builds the default template url.
	 *
	 * @param name
	 *            the name
	 * @param templateLocation
	 *            the template location
	 * @return the string
	 */
	private static String buildDefaultTemplateUrl(String name,
			Optional<String> templateLocation) {
		return new StringBuilder()
				.append(templateLocation
				.orElse(DEFAULT_REPORT_TEMPLATE_LOCATION)).append(name)
				.append(REPORT_TEMPLATE_FORMAT).toString();
	}
}
