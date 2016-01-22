package gov.samhsa.mhc.pcm.service.consent;

import gov.samhsa.mhc.pcm.service.dto.ConsentDto;
import gov.samhsa.mhc.pcm.service.dto.ConsentValidationDto;

public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
