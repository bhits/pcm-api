package gov.samhsa.acs.polrep.client.dto;

public class PolicyMetadataDto {

	private String id;
	private boolean valid;

	public String getId() {
		return id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
