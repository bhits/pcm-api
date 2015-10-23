package gov.samhsa.pcm.service.report;

import gov.samhsa.pcm.infrastructure.report.AbstractReportConfig;
import gov.samhsa.pcm.infrastructure.report.ReportProps;
import gov.samhsa.pcm.infrastructure.report.SqlScriptProvider;
import gov.samhsa.pcm.service.report.exception.SqlScriptFileException;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * This {@link SqlScriptProvider} implementation reads and provides the SQL from
 * a file in classpath.<br>
 * <br>
 * {@link ReportProps#getSqlScriptFileLocation} is used to locate the SQL file.
 * This class throws {@link SqlScriptFileException} at initialization time if
 * the file SQL file cannot be found or at {@link #getSqlScript()} invocation
 * time if the file cannot be read.
 *
 * @see SqlScriptProvider
 */
public class ClasspathSqlScriptProvider implements SqlScriptProvider {

	/** The Constant UTF_8. */
	private static final String UTF_8 = "UTF-8";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The sql script file location. */
	private final String sqlScriptFileLocation;

	/**
	 * Instantiates a new classpath sql script provider.
	 *
	 * @param reportConfig
	 *            the report config
	 * @throws SqlScriptFileException
	 *             the sql script file exception
	 */
	public ClasspathSqlScriptProvider(AbstractReportConfig reportConfig)
			throws SqlScriptFileException {
		super();
		this.sqlScriptFileLocation = reportConfig.getReportProps()
				.getSqlScriptFileLocation();
		verifySqlScriptFileExists(reportConfig);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.infrastructure.report.SqlScriptProvider#getSqlScript
	 * ()
	 */
	@Override
	public String getSqlScript() throws SqlScriptFileException {
		try {
			final Resource sqlResource = new ClassPathResource(
					this.sqlScriptFileLocation);
			final String sql = IOUtils.toString(sqlResource.getInputStream(),
					UTF_8);
			logger.debug("Report SQL Script:");
			logger.debug(sql);
			return sql;
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
			throw new SqlScriptFileException(e);
		}

	}

	/**
	 * Creates the sql not fount exception message.
	 *
	 * @param reportConfig
	 *            the report config
	 * @return the string
	 */
	private String createSqlNotFountExceptionMessage(
			AbstractReportConfig reportConfig) {
		return new StringBuilder().append("SQL script file for report ")
				.append(reportConfig.getReportProps().getName())
				.append(" cannot be found!").toString();
	}

	/**
	 * Verify sql script file exists.
	 *
	 * @param reportConfig
	 *            the report config
	 */
	private void verifySqlScriptFileExists(AbstractReportConfig reportConfig) {
		if (!new ClassPathResource(this.sqlScriptFileLocation).exists()) {
			final String errorMessage = createSqlNotFountExceptionMessage(reportConfig);
			logger.error(errorMessage);
			throw new SqlScriptFileException(errorMessage);
		}
	}
}
