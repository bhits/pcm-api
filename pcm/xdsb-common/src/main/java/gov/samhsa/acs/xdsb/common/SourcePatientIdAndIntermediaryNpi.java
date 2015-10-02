package gov.samhsa.acs.xdsb.common;

/**
 * The Class SourcePatientIdAndIntermediaryNpi.
 */
public class SourcePatientIdAndIntermediaryNpi {

	/** The xdsb document reference. */
	private XdsbDocumentReference xdsbDocumentReference;

	/** The source patient id. */
	private String sourcePatientId;

	/** The intermediary npi. */
	private String intermediaryNpi;

	/**
	 * Instantiates a new source patient id and intermediary npi.
	 *
	 * @param xdsbDocumentReference
	 *            the xdsb document reference
	 * @param sourcePatientId
	 *            the source patient id
	 * @param intermediaryNpi
	 *            the intermediary npi
	 */
	public SourcePatientIdAndIntermediaryNpi(
			XdsbDocumentReference xdsbDocumentReference,
			String sourcePatientId, String intermediaryNpi) {
		super();
		this.xdsbDocumentReference = xdsbDocumentReference;
		this.sourcePatientId = sourcePatientId;
		this.intermediaryNpi = intermediaryNpi;
	}

	/**
	 * Gets the intermediary npi.
	 *
	 * @return the intermediary npi
	 */
	public String getIntermediaryNpi() {
		return intermediaryNpi;
	}

	/**
	 * Gets the source patient id.
	 *
	 * @return the source patient id
	 */
	public String getSourcePatientId() {
		return sourcePatientId;
	}

	/**
	 * Gets the xdsb document reference.
	 *
	 * @return the xdsb document reference
	 */
	public XdsbDocumentReference getXdsbDocumentReference() {
		return xdsbDocumentReference;
	}

	/**
	 * Sets the intermediary npi.
	 *
	 * @param intermediaryNpi
	 *            the new intermediary npi
	 */
	public void setIntermediaryNpi(String intermediaryNpi) {
		this.intermediaryNpi = intermediaryNpi;
	}

	/**
	 * Sets the source patient id.
	 *
	 * @param sourcePatientId
	 *            the new source patient id
	 */
	public void setSourcePatientId(String sourcePatientId) {
		this.sourcePatientId = sourcePatientId;
	}

	/**
	 * Sets the xdsb document reference.
	 *
	 * @param xdsbDocumentReference
	 *            the new xdsb document reference
	 */
	public void setXdsbDocumentReference(
			XdsbDocumentReference xdsbDocumentReference) {
		this.xdsbDocumentReference = xdsbDocumentReference;
	}
}
