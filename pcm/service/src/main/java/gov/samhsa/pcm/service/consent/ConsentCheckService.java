package gov.samhsa.pcm.service.consent;

import gov.samhsa.pcm.service.dto.ConsentDto;
import gov.samhsa.pcm.service.dto.ConsentValidationDto;


public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
