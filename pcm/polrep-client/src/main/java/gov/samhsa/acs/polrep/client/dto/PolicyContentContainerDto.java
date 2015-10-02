package gov.samhsa.acs.polrep.client.dto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PolicyContentContainerDto {

	private List<PolicyContentDto> policies;

	public PolicyContentContainerDto() {
		super();
		this.policies = new LinkedList<PolicyContentDto>();
	}

	public PolicyContentContainerDto(List<PolicyContentDto> policies) {
		super();
		this.policies = policies;
	}

	public PolicyContentContainerDto(PolicyContentDto... addPolicyDtos) {
		super();
		this.policies = Arrays.asList(addPolicyDtos);
	}

	public List<PolicyContentDto> getPolicies() {
		return policies;
	}

	public void setPolicies(List<PolicyContentDto> policies) {
		this.policies = policies;
	}

}
