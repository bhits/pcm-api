package gov.samhsa.c2s.pcm.infrastructure.securityevent;

import gov.samhsa.c2s.common.audit.PredicateKey;

public enum SecurityPredicateKey implements PredicateKey {
    IP_ADDRESS;

    @Override
    public String getPredicateKey() {
        return this.toString();
    }
}
