package gov.samhsa.bhits.pcm.infrastructure.securityevent;

import gov.samhsa.bhits.common.audit.PredicateKey;

public enum SecurityPredicateKey implements PredicateKey {
    IP_ADDRESS;

    @Override
    public String getPredicateKey() {
        return this.toString();
    }
}
