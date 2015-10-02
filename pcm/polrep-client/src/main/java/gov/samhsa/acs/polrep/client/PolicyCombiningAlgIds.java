package gov.samhsa.acs.polrep.client;

public enum PolicyCombiningAlgIds {
	FIRST_APPLICABLE(
			"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:first-applicable"), ORDERED_DENY_OVERRIDES(
			"urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-deny-overrides"), ORDERED_PERMIT_OVERRIDES(
			"urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-permit-overrides"), DENY_OVERRIDES(
			"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides"), ONLY_ONE_APPLICABLE(
			"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:only-one-applicable"), PERMIT_OVERRIDES(
			"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides");

	private String urn;

	private PolicyCombiningAlgIds(String urn) {
		this.urn = urn;
	}

	public String getUrn() {
		return urn;
	}
}
