package gov.samhsa.consent2share.infrastructure.report;

/**
 * The Enum ReportFormat.
 */
public enum ReportFormat {

	/** The html. */
	HTML("html"),
	/** The pdf. */
	PDF("pdf"),
	/** The xls. */
	XLS("xls"),
	/** The csv. */
	CSV("csv");

	/** The format. */
	private String format;

	/**
	 * Instantiates a new report format.
	 *
	 * @param format
	 *            the format
	 */
	ReportFormat(String format) {
		this.format = format;
	}

	/**
	 * Gets the format.
	 *
	 * @return the format
	 */
	public String getFormat() {
		return this.format;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getFormat();
	}

}
