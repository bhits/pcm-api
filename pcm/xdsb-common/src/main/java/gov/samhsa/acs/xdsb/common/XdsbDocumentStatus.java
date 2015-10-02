package gov.samhsa.acs.xdsb.common;

/**
 * The Enum XdsbDocumentStatus.
 */
public enum XdsbDocumentStatus {

	/** The submitted. */
	SUBMITTED("urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted"),

	/** The approved. */
	APPROVED("urn:oasis:names:tc:ebxml-regrep:StatusType:Approved"),

	/** The deprecated. */
	DEPRECATED("urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated");

	/** The urn. */
	private final String urn;

	/**
	 * Instantiates a new xdsb document status.
	 *
	 * @param urn
	 *            the urn
	 */
	XdsbDocumentStatus(String urn) {
		this.urn = urn;
	}

	/**
	 * Gets the urn.
	 *
	 * @return the urn
	 */
	public String getUrn() {
		return urn;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getUrn();
	}
}
