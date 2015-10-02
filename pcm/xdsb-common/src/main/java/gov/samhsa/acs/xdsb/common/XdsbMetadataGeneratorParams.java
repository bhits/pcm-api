package gov.samhsa.acs.xdsb.common;

/**
 * The Enum XdsbMetadataGeneratorParams.
 */
public enum XdsbMetadataGeneratorParams {

	/** The Constant HomeCommunityId_Parameter_Name. */
	HomeCommunityId_Parameter_Name("homeCommunityId"),

	/** The Constant PatientUniqueId_Parameter_Name. */
	PatientUniqueId_Parameter_Name("patientUniqueId"),

	/** The Source patient id_ parameter_ name. */
	SourcePatientId_Parameter_Name("sourcePatientId"),

	/** The Source patient domain id_ parameter_ name. */
	SourcePatientDomainId_Parameter_Name("sourcePatientDomainId"),

	/** The Constant XdsDocumentEntry_UniqueId_Parameter_Name. */
	XdsDocumentEntry_UniqueId_Parameter_Name("XDSDocumentEntry_uniqueId"),

	/** The Constant XdsSubmissionSet_UniqueId_Parameter_Name. */
	XdsSubmissionSet_UniqueId_Parameter_Name("XDSSubmissionSet_uniqueId"),

	/** The Constant XdsDocumentEntry_EntryUUID_Parameter_Name. */
	XdsDocumentEntry_EntryUUID_Parameter_Name("XDSDocumentEntry_entryUUID");

	/** The param name. */
	private final String paramName;

	/**
	 * Instantiates a new xdsb metadata generator params.
	 *
	 * @param paramName
	 *            the param name
	 */
	XdsbMetadataGeneratorParams(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * Gets the param name.
	 *
	 * @return the param name
	 */
	public String getParamName() {
		return paramName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getParamName();
	}
}
