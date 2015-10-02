package gov.samhsa.acs.polrep.client.dto;

public class PolicyContentDto {

	private byte[] policy;

	public PolicyContentDto() {
		super();
	}

	public PolicyContentDto(byte[] policy) {
		super();
		this.policy = policy;
	}

	public byte[] getPolicy() {
		return policy;
	}

	public void setPolicy(byte[] policy) {
		this.policy = policy;
	}
}
