package gov.samhsa.c2s.pcm.service.consentexport;

import gov.samhsa.c2s.pcm.domain.consent.Consent;
import gov.samhsa.mhc.common.consentgen.ConsentDto;

public interface ConsentExportMapper {

	ConsentDto map(Consent consent);

}
