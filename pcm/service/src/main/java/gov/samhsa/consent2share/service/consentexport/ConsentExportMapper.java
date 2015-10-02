package gov.samhsa.consent2share.service.consentexport;

import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent2share.domain.consent.Consent;

public interface ConsentExportMapper {

	ConsentDto map(Consent consent);

}
