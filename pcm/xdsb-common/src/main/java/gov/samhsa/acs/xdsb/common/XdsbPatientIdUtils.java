package gov.samhsa.acs.xdsb.common;

public final class XdsbPatientIdUtils {

	private static final String SINGLE_QUOTE = "'";
	private static final String CARETS = "^^^";
	private static final String ISO = "ISO";
	private static final String AMPERSAND = "&";
	private static final String AMPERSAND_XML_ENCODED = "&amp;";

	private XdsbPatientIdUtils() {
	}

	public static final String toFullyQualifiedPatientIdentifier(
			String patientId, String domainId) {
		return createPIDBuilder(patientId, domainId, AMPERSAND).toString();
	}

	public static final String toFullyQualifiedPatientIdentifierWrappedWithSingleQuotes(
			String patientId, String domainId) {
		return createPIDBuilder(patientId, domainId, AMPERSAND)
				.insert(0, SINGLE_QUOTE).append(SINGLE_QUOTE).toString();
	}

	public static final String toFullyQualifiedPatientIdentifierWrappedWithSingleQuotesXmlEncoded(
			String patientId, String domainId) {
		return createPIDBuilder(patientId, domainId, AMPERSAND_XML_ENCODED)
				.insert(0, SINGLE_QUOTE).append(SINGLE_QUOTE).toString();
	}

	public static final String toFullyQualifiedPatientIdentifierXmlEncoded(
			String patientId, String domainId) {
		return createPIDBuilder(patientId, domainId, AMPERSAND_XML_ENCODED)
				.toString();
	}

	private static final StringBuilder createPIDBuilder(String patientId,
			String domainId, String ampersand) {
		return new StringBuilder().append(patientId).append(CARETS)
				.append(ampersand).append(domainId).append(ampersand)
				.append(ISO);
	}
}
