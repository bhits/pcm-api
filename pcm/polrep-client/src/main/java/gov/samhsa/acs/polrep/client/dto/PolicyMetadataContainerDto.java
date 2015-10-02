package gov.samhsa.acs.polrep.client.dto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PolicyMetadataContainerDto {

	private List<PolicyMetadataDto> policies;

	public PolicyMetadataContainerDto() {
		super();
		this.policies = new LinkedList<PolicyMetadataDto>();
	}

	public PolicyMetadataContainerDto(List<PolicyMetadataDto> policies) {
		super();
		this.policies = policies;
	}

	public PolicyMetadataContainerDto(PolicyMetadataDto... policies) {
		super();
		this.policies = Arrays.asList(policies);
	}

	public List<PolicyMetadataDto> getPolicies() {
		return policies;
	}

	public void setPolicies(List<PolicyMetadataDto> policies) {
		this.policies = policies;
	}
}
