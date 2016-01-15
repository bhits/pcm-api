package gov.samhsa.bhits.consentgen;

public enum XslResource {

	CDAR2XSLNAME("c2cdar2.xsl"), XACMLXSLNAME("c2xacml.xsl"), XACMLPDFCONSENTFROMXSLNAME(
			"c2xacmlpdfConsentFrom.xsl"), XACMLPDFCONSENTTOXSLNAME(
			"c2xacmlpdfConsentTo.xsl");

	private String fileName;

	XslResource(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return this.fileName;
	}
}
