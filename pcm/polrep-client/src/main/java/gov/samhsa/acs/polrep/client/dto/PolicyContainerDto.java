package gov.samhsa.acs.polrep.client.dto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PolicyContainerDto {

	private List<PolicyDto> policies;

	public PolicyContainerDto() {
		super();
		this.policies = new LinkedList<PolicyDto>();
	}

	public PolicyContainerDto(List<PolicyDto> policies) {
		super();
		this.policies = policies;
	}

	public PolicyContainerDto(PolicyDto... getPolicyDtos) {
		super();
		this.policies = Arrays.asList(getPolicyDtos);
	}

	public List<PolicyDto> getPolicies() {
		return policies;
	}

	public void setPolicies(List<PolicyDto> policies) {
		this.policies = policies;
	}

}
