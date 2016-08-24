package gov.samhsa.c2s.pcm.service.consent;

import gov.samhsa.c2s.pcm.service.dto.ConsentDto;
import gov.samhsa.c2s.pcm.service.dto.ConsentValidationDto;

public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
