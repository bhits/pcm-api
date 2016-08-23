package gov.samhsa.c2s.pcm.infrastructure.securityevent;

import gov.samhsa.c2s.common.audit.AuditVerb;

public enum SecurityAuditVerb implements AuditVerb {
    FAILED_ATTEMPTS_TO_LOGIN_AS,
    DOWNLOADS_FILE,
    UPLOADS_FILE,
    UPLOADS_MALICIOUS_FILE,
    ATTEMPTS_TO_ACCESS_UNAUTHORIZED_RESOURCE,
    CREATES_USER;

    @Override
    public String getAuditVerb() {
        return this.toString();
    }

}
