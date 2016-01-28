package gov.samhsa.mhc.pcm.infrastructure.securityevent;

import gov.samhsa.mhc.common.audit.PredicateKey;

public enum SecurityPredicateKey implements PredicateKey {
    IP_ADDRESS;

    @Override
    public String getPredicateKey() {
        return this.toString();
    }
}
