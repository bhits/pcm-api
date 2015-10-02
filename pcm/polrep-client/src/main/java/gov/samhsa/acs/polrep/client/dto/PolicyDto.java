package gov.samhsa.acs.polrep.client.dto;

public class PolicyDto {

	private String id;
	private boolean valid;
	private byte[] policy;

	public String getId() {
		return id;
	}

	public byte[] getPolicy() {
		return policy;
	}

	public boolean isValid() {
		return valid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPolicy(byte[] policy) {
		this.policy = policy;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
