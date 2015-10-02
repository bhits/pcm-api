package gov.samhsa.acs.common.xacml;

/**
 * The Enum XacmlRequestContextElement.
 */
public enum XacmlRequestContextElement {

	/** The subject. */
	SUBJECT("Subject"),

	/** The resource. */
	RESOURCE("Resource"),

	/** The action. */
	ACTION("Action"),

	/** The environment. */
	ENVIRONMENT("Environment");

	/** The element name. */
	private final String elementName;

	/**
	 * Instantiates a new xacml request context element.
	 *
	 * @param elementName
	 *            the element name
	 */
	XacmlRequestContextElement(String elementName) {
		this.elementName = elementName;
	}

	/**
	 * Gets the element name.
	 *
	 * @return the element name
	 */
	public String getElementName() {
		return elementName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getElementName();
	}
}
