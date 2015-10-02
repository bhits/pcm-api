package gov.samhsa.consent2share.infrastructure.securityevent;

import gov.samhsa.acs.audit.PredicateKey;

public enum SecurityPredicateKey implements PredicateKey{
	IP_ADDRESS;

	@Override
	public String getPredicateKey() {
		return this.toString();
	}
}
