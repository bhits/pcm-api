package gov.samhsa.c2s.pcm.infrastructure;

/**
 * An interface that returns a sql script string that can be used by the report
 * data providers.
 *
 * @see
 */
public interface SqlScriptProvider {

	/**
	 * Gets the sql script.
	 *
	 * @return the sql script
	 */
	public abstract String getSqlScript();

}
