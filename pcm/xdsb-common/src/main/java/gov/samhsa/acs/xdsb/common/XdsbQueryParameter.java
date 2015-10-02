package gov.samhsa.acs.xdsb.common;

/**
 * The Interface XdsbQueryParameter.
 */
public interface XdsbQueryParameter {

	/**
	 * Gets the parameter.
	 *
	 * @return the parameter
	 */
	public abstract XdsbQueryParameterValue getParameter();

	/**
	 * Gets the parameter name.
	 *
	 * @return the parameter name
	 */
	public default String getParameterName() {
		return getParameter().getParameterName();
	}
}
