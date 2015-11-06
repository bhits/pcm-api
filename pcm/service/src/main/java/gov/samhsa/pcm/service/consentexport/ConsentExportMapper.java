package gov.samhsa.pcm.service.consentexport;

import gov.samhsa.consent.ConsentDto;
import gov.samhsa.pcm.domain.consent.Consent;

public interface ConsentExportMapper {

	ConsentDto map(Consent consent);

}
