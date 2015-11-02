package gov.samhsa.consent2share.service.consent;

import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentValidationDto;

import org.springframework.security.access.annotation.Secured;

public interface ConsentCheckService {

	public ConsentValidationDto getConflictConsent(ConsentDto consentDto);

}
