package gov.samhsa.bhits.pcm.service.consentexport;

import gov.samhsa.bhits.consentgen.ConsentDto;
import gov.samhsa.bhits.pcm.domain.consent.Consent;

public interface ConsentExportMapper {

	ConsentDto map(Consent consent);

}
