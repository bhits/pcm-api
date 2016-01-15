package gov.samhsa.bhits.pcm.service.consent;

import gov.samhsa.bhits.pcm.service.dto.ConsentDto;
import gov.samhsa.bhits.pcm.service.dto.ConsentValidationDto;

public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
