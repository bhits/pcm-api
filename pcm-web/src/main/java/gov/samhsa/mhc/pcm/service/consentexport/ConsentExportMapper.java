package gov.samhsa.mhc.pcm.service.consentexport;

import gov.samhsa.mhc.common.consentgen.ConsentDto;
import gov.samhsa.mhc.pcm.domain.consent.Consent;

public interface ConsentExportMapper {

	ConsentDto map(Consent consent);

}
