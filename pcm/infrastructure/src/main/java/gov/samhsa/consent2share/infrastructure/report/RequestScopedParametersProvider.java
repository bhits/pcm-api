package gov.samhsa.consent2share.infrastructure.report;

public interface RequestScopedParametersProvider {

	/**
	 * Gets the request scoped parameters.
	 *
	 * @return the request scoped parameters
	 */
	public abstract RequestScopedParameters getRequestScopedParameters();
}