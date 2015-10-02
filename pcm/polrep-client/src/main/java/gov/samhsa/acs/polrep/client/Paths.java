package gov.samhsa.acs.polrep.client;

public enum Paths {
	POLICIES("/policies"), POLICIES_WITH_PARAM("/policies/{policyId}"), POLICIES_COMBINED_WITH_PARAM(
			"/policies/{policyId}/combined"), POLICY_COMBINING_ALG_IDS(
			"/policyCombiningAlgIds");

	private static final String POLICY_ID_PARAM_NAME = "{policyId}";

	private String path;

	private Paths(String path) {
		this.path = path;
	}

	public String getPath() {
		if (this.path.contains(POLICY_ID_PARAM_NAME)) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("This path requires a parameter value! Path: ");
			errorBuilder.append(this.path);
			errorBuilder.append("; ParameterName: ");
			errorBuilder.append(POLICY_ID_PARAM_NAME);
			throw new IllegalStateException(errorBuilder.toString());
		}
		return this.path;
	}

	public String getPath(String paramValue) {
		if (!this.path.contains(POLICY_ID_PARAM_NAME)) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder
					.append("This path doesn't support parameterization! Path: ");
			errorBuilder.append(this.path);
			throw new IllegalStateException(errorBuilder.toString());
		}
		return this.path.replace(POLICY_ID_PARAM_NAME, paramValue);
	}

}
