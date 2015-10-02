package gov.samhsa.acs.common.xacml;

/**
 * The Interface XacmlAttributeId.
 */
public interface XacmlAttributeId {

	/**
	 * Gets the urn.
	 *
	 * @return the urn
	 */
	public default String getUrn() {
		return getXacmlAttributeIdValue().getUrn();
	}

	/**
	 * Gets the xacml attribute id value.
	 *
	 * @return the xacml attribute id value
	 */
	public abstract XacmlAttributeIdValue getXacmlAttributeIdValue();
}
