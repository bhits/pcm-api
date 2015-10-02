package gov.samhsa.acs.xdsb.common;

/**
 * The Enum XdsbMetadataFormatCode.
 */
public enum XdsbMetadataFormatCode {

	// Supported XDS.b Format Codes
	/** The Constant FORMAT_CODE_CLINICAL_DOCUMENT. */
	FORMAT_CODE_CLINICAL_DOCUMENT("'2.16.840.1.113883.10.20.1^^HITSP'"),

	/** The Constant FORMAT_CODE_PRIVACY_CONSENT. */
	FORMAT_CODE_PRIVACY_CONSENT("'1.3.6.1.4.1.19376.1.5.3.1.1.7^^IHE BPPC'");

	/** The format code. */
	private final String formatCode;

	/**
	 * Instantiates a new xdsb metadata format code.
	 *
	 * @param formatCode
	 *            the format code
	 */
	XdsbMetadataFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}

	/**
	 * Gets the format code.
	 *
	 * @return the format code
	 */
	public String getFormatCode() {
		return formatCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getFormatCode();
	}
}
