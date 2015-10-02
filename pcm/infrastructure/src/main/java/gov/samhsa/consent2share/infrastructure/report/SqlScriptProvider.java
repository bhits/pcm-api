package gov.samhsa.consent2share.infrastructure.report;

/**
 * An interface that returns a sql script string that can be used by the report
 * data providers.
 *
 * @see JdbcTemplateReportDataProvider
 */
public interface SqlScriptProvider {

	/**
	 * Gets the sql script.
	 *
	 * @return the sql script
	 */
	public abstract String getSqlScript();

}
