package gov.samhsa.consent2share.infrastructure.report;

/**
 * The Interface ReportResourceResolver. This is a very generic interface that
 * requires resource resolution by resource name from both classpath and webpath
 * locations.
 */
public interface ReportResourceResolver {

	/**
	 * Absolute classpath location of the resource with the given resource name.
	 *
	 * @param resourceName
	 *            the resource name
	 * @return absolute classpath location for the resource
	 */
	public abstract String classpath(String resourceName);

	/**
	 * Absolute webpath location of the resource with the given resource name.
	 *
	 * @param resourceName
	 *            the resource name
	 * @return absolute webpath location for the resource
	 */
	public abstract String webpath(String resourceName);

}